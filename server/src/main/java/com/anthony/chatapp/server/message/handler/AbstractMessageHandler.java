package com.anthony.chatapp.server.message.handler;


import com.anthony.chatapp.core.protocol.message.Message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/6/30.
 */
public abstract class AbstractMessageHandler implements MessageHandler {

    protected SocketChannel sourceSocketChannel;
    protected Message message;

    public AbstractMessageHandler(Message message, SocketChannel sourceSocketChannel) {
        this.sourceSocketChannel = sourceSocketChannel;
        this.message = message;
        finishMessage();
    }

    private void finishMessage() {
        int bodyLength = message.getBodyLength();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bodyLength);
        byteBuffer.clear();
        byte[] body = new byte[bodyLength];
        try {
            sourceSocketChannel.read(byteBuffer);
            byteBuffer.flip();
            byteBuffer.get(body);
            message.setBody(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        handle();
    }
}
