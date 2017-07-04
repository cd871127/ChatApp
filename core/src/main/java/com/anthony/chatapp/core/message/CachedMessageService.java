package com.anthony.chatapp.core.message;

import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.sender.Sender;

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
public class CachedMessageService implements Runnable {
    private Map<String, Message> cache = new HashMap<>();

    private static CachedMessageService ourInstance = new CachedMessageService();

    public static CachedMessageService getInstance() {
        return ourInstance;
    }

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private Sender sender;

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
        cache.put(key, message);
        lock.writeLock().unlock();
    }

    public void removeMessage(String key) {
        lock.writeLock().lock();
        cache.remove(key);
        lock.writeLock().unlock();
    }


    @Override
    public void run() {
        while (!isShutdown) {
            Set<String> timeoutMessage = new HashSet<>();
            lock.readLock().lock();
            System.out.println(cache.size());
            long currentTime = System.currentTimeMillis();
            cache.forEach((k, v) -> {
                long elapse = currentTime - Long.valueOf(v.getTimestamp());
                if (elapse >= 60000) {
                    System.out.println("bbbb");
                    timeoutMessage.add(k);
                    System.out.println("resend");
                    sender.send(v);
                }
            });
            lock.readLock().unlock();

            lock.writeLock().lock();
            timeoutMessage.forEach((k) -> cache.remove(k));
            lock.writeLock().unlock();

            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
