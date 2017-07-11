package com.anthony.chatapp.core.message;

import com.anthony.chatapp.core.message.entity.CachedMessage;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.sender.Sender;
import com.anthony.chatapp.core.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.anthony.chatapp.core.Const.isShutdown;

/**
 * Created by chend on 2017/7/4.
 * message的缓存队列,用于保存已发送的message,接收到确认信息收,删除
 */
public class CachedMessageService extends Service {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //messageid and message
    private Map<String, CachedMessage> cache = new HashMap<>();

    private static CachedMessageService ourInstance = new CachedMessageService();

    public static CachedMessageService getInstance() {
        return ourInstance;
    }

    public static CachedMessageService getInstance(Sender sender) {
        CachedMessageService.sender = sender;
        return getInstance();
    }

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private static Sender sender;

    private CachedMessageService() {
    }

    public boolean contains(String key) {
        boolean res;
        lock.readLock().lock();
        res = cache.containsKey(key);
        lock.readLock().unlock();
        return res;
    }

    public void addMessage(String key, Message message) {
        lock.writeLock().lock();
        logger.debug("add to Cache: " + message.getId());
        cache.put(key, new CachedMessage(message, System.currentTimeMillis()));
        lock.writeLock().unlock();
//        notify();
    }

    public void removeMessage(String key) {
        lock.writeLock().lock();
        logger.debug("remove from Cache: " + key);
        cache.remove(key);
        lock.writeLock().unlock();
    }


    @Override
    public void run() {
        while (!isShutdown) {

            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


//            if (cache.isEmpty()) {
//                try {
//                    wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

            lock.readLock().lock();
            Set<String> timeoutMessage = new HashSet<>();
            long currentTime = System.currentTimeMillis();
            cache.forEach((k, v) -> {
                long elapse = currentTime - v.getCachedTime();
                if (elapse >= 10000) {
                    sender.send(v.getMessage());
                }
                elapse = currentTime - Long.valueOf(v.getMessage().getTimestamp());
                if (elapse >= 60000) {
                    timeoutMessage.add(k);
                }
            });
            lock.readLock().unlock();

            if (!timeoutMessage.isEmpty()) {
                lock.writeLock().lock();
                timeoutMessage.forEach((k) -> cache.remove(k));
                lock.writeLock().unlock();
            }


        }
    }

}
