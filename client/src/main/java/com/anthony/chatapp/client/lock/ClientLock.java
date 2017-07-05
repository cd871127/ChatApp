package com.anthony.chatapp.client.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by chend on 2017/7/3.
 * 同步服务器通信的key
 */
public class ClientLock {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private ClientLock() {
    }

    public static void lockRead() {
        lock.readLock().lock();
    }

    public static void lockWrite() {
        lock.writeLock().lock();
    }

    public static void unLockRead() {
        lock.readLock().unlock();
    }

    public static void unLockWrite() {
        lock.writeLock().unlock();
    }
}
