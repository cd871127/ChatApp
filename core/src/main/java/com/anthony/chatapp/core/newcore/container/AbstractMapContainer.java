package com.anthony.chatapp.core.newcore.container;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by chend on 2017/7/12.
 */
public abstract class AbstractMapContainer<V> implements Container<V> {
    private Map<String, V> container = new HashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

//    @Override
//    private void add(V v) {
//
//    }

    @Override
    public V remove(V v) {
        return null;
    }

    @Override
    public V removeById(String id) {
        lock.writeLock().lock();
        V v = container.remove(id);
        lock.writeLock().unlock();
        return v;
    }

    @Override
    public V getById(String id) {
        lock.readLock().lock();
        V v = container.get(id);
        lock.readLock().unlock();
        return v;
    }
}
