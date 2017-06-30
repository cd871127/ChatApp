package com.anthony.chatapp.server;

import com.anthony.chatapp.core.Const;
import com.anthony.chatapp.core.protocol.processor.handler.MessageReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by chend on 2017/6/30.
 */
public class MessageReceiveService implements Runnable {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, SocketChannel> clients = new HashMap<>();
    private Selector selector;
    private ReentrantLock lock = new ReentrantLock();
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    public MessageDispatchService mds;

    public MessageReceiveService() throws IOException {
        selector = Selector.open();
    }

    public void addChannel(SocketChannel channel) {
        logger.debug("add channel");
        try {
            channel.configureBlocking(false);
            if (channel.isConnectionPending())
                channel.finishConnect();
            lock.lock();
            selector.wakeup();
            channel.register(selector, SelectionKey.OP_READ);
            lock.unlock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!Const.isShutdown) {
                if (lock.isLocked())
                    continue;
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isValid() && (key.attachment() == null || !(key.attachment().equals(true)))) {
                        //添加附件,防止key还没有处理完,状态没更新,启动了新的线程,那么就是两个线程处理同一个key
                        key.attach(true);
                        mds.addMessage(executorService.submit(new MessageReceiver(key)));
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


}
