package com.anthony.chatapp.core.newcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by chend on 2017/7/12.
 */
public class Selector {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private java.nio.channels.Selector selector;
    private ReentrantLock lock;
    private Condition condition;

    private static Selector instance = new Selector();

    public static Selector getInstance() {
        return instance;
    }

    private Selector() {
        try {
            selector = java.nio.channels.Selector.open();
        } catch (IOException e) {
            logger.error("open selector failed");
            e.printStackTrace();
        }
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }


    public SelectionKey registChannel(SocketChannel socketChannel) {
        SelectionKey key = null;
        try {
            socketChannel.configureBlocking(false);
            if (socketChannel.isConnectionPending())
                socketChannel.finishConnect();
            lock.lock();
            selector.wakeup();
            key = socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            condition.signal();
        }
        return key;
    }

    public List<SelectionKey> readableKeys() throws IOException {
        if (lock.isLocked()) {
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        selector.select();
        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

        ArrayList<SelectionKey> keys = new ArrayList<>();
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            if (key.isReadable()) {
                //设置对读没兴趣 以防重复处理key
                key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
//                mds.addMessage(executorService.submit(new MessageReceiver(key,this)));
                keys.add(key);
            }
            keyIterator.remove();
        }
        return keys.isEmpty() ? null : keys;
    }

    public void setKeyInterestOnRead(SelectionKey key) {
        lock.lock();
        selector.wakeup();
        key.interestOps(key.interestOps() | SelectionKey.OP_READ);
        lock.unlock();
    }

}
