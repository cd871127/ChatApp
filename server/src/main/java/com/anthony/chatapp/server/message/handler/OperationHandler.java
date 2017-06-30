package com.anthony.chatapp.server.message.handler;

import com.anthony.chatapp.core.protocol.message.Message;

import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/6/30.
 */
public class OperationHandler extends AbstractMessageHandler {
    public OperationHandler(Message message, SocketChannel sourceSocketChannel) {
        super(message, sourceSocketChannel);
    }

    @Override
    public void handle() {
        System.out.println(message);
    }
}
