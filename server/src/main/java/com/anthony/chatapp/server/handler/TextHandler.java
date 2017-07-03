package com.anthony.chatapp.server.handler;

import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.handler.AbstractMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/7/1.
 */
public class TextHandler extends AbstractMessageHandler {
    private static Logger logger = LoggerFactory.getLogger(TextHandler.class);

    public TextHandler(MessageAndKey messageAndKey) {
        super(messageAndKey);
    }

    @Override
    public void handle() {
        //写入到消息队列中 消息队列自己发送消息
        try {
            messageIntoSocketChannel(messageAndKey.getMessage(), (SocketChannel) messageAndKey.getKey().channel());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(messageAndKey.getMessage());
    }

    int messageIntoSocketChannel(Message message, SocketChannel socketChannel) throws IOException {
        byte[] messageByte = message.encode();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(messageByte.length);
        byteBuffer.put(messageByte);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        return messageByte.length;
    }
}
