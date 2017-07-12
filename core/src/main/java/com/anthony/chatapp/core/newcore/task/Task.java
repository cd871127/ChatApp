package com.anthony.chatapp.core.newcore.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by chend on 2017/7/12.
 */
public abstract class Task<V> implements Callable<V> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract V execute();

    @Override
    public V call() {
        return execute();
    }
}
