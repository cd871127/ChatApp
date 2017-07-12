package com.anthony.chatapp.core.newcore.container;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by chend on 2017/7/12.
 */
public abstract class AbstractBlockQueueContainer<V> implements Container<V> {
    private BlockingQueue<V> container = new LinkedBlockingQueue<>();

    @Override
    public void add(V v) {

    }

    @Override
    public V remove(V v) {
        return null;
    }
    @Override
    public V removeById(String id) {
        return null;
    }
    @Override
    public V getById(String id) {
        return null;
    }
}
