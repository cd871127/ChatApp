package com.anthony.chatapp.core.service;

import com.anthony.chatapp.core.Const;
import com.anthony.chatapp.core.service.receiver.MessageReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by chend on 2017/6/30.
 */
public class MessageReceiveService extends Service {
    private static Logger logger = LoggerFactory.getLogger(MessageReceiveService.class);
    private Selector selector;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private MessageDispatchService mds;


    public MessageReceiveService(MessageDispatchService mds) throws IOException {
        selector = Selector.open();
        this.mds = mds;
    }

    public SelectionKey addChannel(SocketChannel channel) {
        logger.debug("add channel");
        SelectionKey key=null;
        try {
            channel.configureBlocking(false);
            if (channel.isConnectionPending())
                channel.finishConnect();
            lock.writeLock().lock();
            selector.wakeup();
            key=channel.register(selector, SelectionKey.OP_READ);
            lock.writeLock().unlock();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return key;
    }

    @Override
    public void run() {
        try {
            while (!Const.isShutdown) {
                if (lock.isWriteLocked())
                    continue;
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        //设置对读没兴趣 以防重复处理key
                        key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
                        mds.addMessage(executorService.submit(new MessageReceiver(key,this)));
                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//        }
        executorService.shutdown();
    }

    public void interestOps(SelectionKey key, int ops) {
        lock.writeLock().lock();
        selector.wakeup();
        key.interestOps(ops);
        lock.writeLock().unlock();
    }

}
