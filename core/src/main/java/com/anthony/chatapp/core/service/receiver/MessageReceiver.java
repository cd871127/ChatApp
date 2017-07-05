package com.anthony.chatapp.core.service.receiver;

import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.service.MessageReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

/**
 * Created by chend on 2017/6/30.
 * 从key中读取数据,转换到Message
 */
public class MessageReceiver implements Callable<MessageAndKey> {
    private static Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
    private SelectionKey key;
    private MessageReceiveService mrs;

    public MessageReceiver(SelectionKey key, MessageReceiveService mrs) {
        this.key = key;
        this.mrs = mrs;
    }

    @Override
    public MessageAndKey call() {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        Message message;
        try {
            byte[] lengthByte = read(socketChannel, Message.HEADER_BYTE_COUNT);
            int bodyLength = Message.getMessageLength(lengthByte);
            byte[] bodyBytes = read(socketChannel, bodyLength);
            message = Message.decode(bodyBytes);
            //设置key对读感兴趣
            mrs.interestOps(key, key.interestOps() | SelectionKey.OP_READ);
        } catch (BufferUnderflowException | IOException e) {
            logger.warn("lose connection");
            return new MessageAndKey(null, key);
        }
        return new MessageAndKey(message, key);
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
