package com.anthony.chatapp.client;

import com.anthony.chatapp.client.lock.ClientLock;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.sender.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/7/4.
 */
public class ClientMessageSender extends MessageSender {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static ClientMessageSender clientMessageSender;
    private SelectionKey key;

    private ClientMessageSender() {
    }

    private ClientMessageSender(SelectionKey key) {
        this.key = key;
    }

    public static ClientMessageSender getInstance() {
        return clientMessageSender;
    }

    public static ClientMessageSender getInstance(SelectionKey key) {
        clientMessageSender = new ClientMessageSender(key);
        return clientMessageSender;
    }

    @Override
    public int send(Message message) {
        ClientLock.lockWrite();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int sendLength = 0;
        try {
            sendLength = messageIntoSocketChannel(message, socketChannel);
        } catch (IOException e) {
            logger.error("send message :" + message.toString() + " failed");
            e.printStackTrace();
        }
        ClientLock.unLockWrite();
        return sendLength;
    }
}
