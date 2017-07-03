package com.anthony.chatapp.core.message.sender;

import com.anthony.chatapp.core.message.entity.Message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/7/3.
 */
public class MessageSender implements Sender {

    @Override
    public int send(Message message, SocketChannel socketChannel) throws IOException {
        byte[] messageByte = message.encode();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(messageByte.length);
        byteBuffer.put(messageByte);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        return messageByte.length;
    }
}
