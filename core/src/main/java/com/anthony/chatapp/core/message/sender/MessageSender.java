package com.anthony.chatapp.core.message.sender;

import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.service.Service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/7/3.
 */
public abstract class MessageSender extends Service implements Sender {

    public void run(){}

    protected int messageIntoSocketChannel(Message message, SocketChannel socketChannel) throws IOException {
        byte[] messageByte = message.encode();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(messageByte.length);
        byteBuffer.put(messageByte);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        logger.debug("-----------------------------------");
        logger.debug(message.toString());
        logger.debug("-----------------------------------");
        return messageByte.length;
    }
}
