package com.anthony.chatapp.server;

import com.anthony.chatapp.core.Const;
import com.anthony.chatapp.core.protocol.constant.Header;
import com.anthony.chatapp.core.protocol.constant.MessageTypes;
import com.anthony.chatapp.core.protocol.message.Message;
import com.anthony.chatapp.server.message.handler.OperationHandler;
import com.anthony.chatapp.server.message.handler.TextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by chend on 2017/6/30.
 */
public class MessageDispatcherService implements Runnable {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String,SocketChannel> clients = new HashMap<>();
    private Selector selector;
    private ReentrantLock lock = new ReentrantLock();
    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    public MessageDispatcherService() throws IOException {
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
                if (!lock.isLocked())
                    selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isValid()) {
                        handleHeader(key);
                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    private void handleHeader(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(Message.HEADER_LENGTH);
        byteBuffer.clear();
        byte[] headerByte = new byte[Message.HEADER_LENGTH];
        try {
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            byteBuffer.get(headerByte);
            Map<String, String> header = Message.decodeHeaders(headerByte);
            clients.put(header.get(Header.FROM),socketChannel);

            String messageType = header.get(Header.TYPE);

            SocketChannel targetChannel=clients.get(header.get(Header.TO));

            Message message = new Message();
            message.setHeaders(header);
            switch (messageType) {
                case MessageTypes.AUDIO:
                    ;
                case MessageTypes.VIDEO:
                    ;
                case MessageTypes.IMAGE:
                    ;
                case MessageTypes.OPERATION: {
                    new OperationHandler(message, socketChannel).run();
                    break;
                }
                case MessageTypes.TEXT: {
                    new TextHandler(message, socketChannel,targetChannel).run();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
