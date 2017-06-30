package com.anthony.chatapp.server.message.handler;

import com.anthony.chatapp.core.protocol.message.Message;

import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by chend on 2017/6/30.
 */
public abstract class ClientToClientMessageHandler extends AbstractMessageHandler {
    protected SocketChannel targetSocketChannel;

    static BlockingQueue<Message> messagesQueue = new LinkedBlockingQueue<>();

    public ClientToClientMessageHandler(Message message, SocketChannel sourceSocketChannel, SocketChannel targetSocketChannel) {
        super(message, sourceSocketChannel);
        this.targetSocketChannel = targetSocketChannel;
    }

    @Override
    public void run() {
        if (null == targetSocketChannel) {
            return;
        }
        handle();
    }
}
