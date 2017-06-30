package com.anthony.chatapp.core.protocol.processor.handler;

import com.anthony.chatapp.core.protocol.message.Message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

/**
 * Created by chend on 2017/6/30.
 */
public class MessageReceiver implements Callable<Message> {
    private SelectionKey key;

    public MessageReceiver(SelectionKey key) {
        this.key = key;
    }

    @Override
    public Message call() throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        System.out.println(key);
        byte[] lengthByte = read(socketChannel, Message.HEADER_BYTE_COUNT);
        int bodyLength = Message.getMessageLength(lengthByte);

        byte[] bodyBytes = read(socketChannel, bodyLength);
        return Message.decode(bodyBytes);
    }

    private byte[] read(SocketChannel socketChannel, int length) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
        byteBuffer.clear();
        byte[] bytes = new byte[length];

        socketChannel.read(byteBuffer);

        byteBuffer.flip();
        byteBuffer.get(bytes);
        return bytes;
    }
}
