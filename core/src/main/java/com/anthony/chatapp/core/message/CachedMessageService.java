package com.anthony.chatapp.core.message;

import com.anthony.chatapp.core.message.entity.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by chend on 2017/7/4.
 * message的缓存队列,用于保存已发送的message,接收到确认信息收,删除
 */
public class CachedMessageService implements Runnable{
    private Map<String, Message> cache = new HashMap<>();

    private static CachedMessageService ourInstance = new CachedMessageService();

    public static CachedMessageService getInstance() {
        return ourInstance;
    }

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private CachedMessageService() {
    }

    public boolean contains(String key) {
        return cache.containsKey(key);
    }

    public void addMessage(String key, Message message) {
        cache.put(key, message);
    }

    public void removeMessage(String key) {
        cache.remove(key);
    }

    @Override
    public void run() {

    }
}
