package com.anthony.chatapp.core.newcore.task;

import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.newcore.Selector;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/7/12.
 */
public class ParseMessageTask extends Task<MessageAndKey> {
    private SelectionKey key;

    public ParseMessageTask(SelectionKey key) {
        this.key = key;
    }

    @Override
    protected MessageAndKey execute() {
        Message message;
        try {
            //处理消息头
            byte[] headerByte = readNByte(Message.TOTAL_HEADER_LENGTH);
            //消息体长度
            int bodyLength = Message.getMessageBodyLength(headerByte);
            //消息类型
            byte type = headerByte[0];
            byte[] bodyBytes = readNByte(bodyLength);

            message = Message.decode(bodyBytes, type);
            //设置key对读感兴趣
        } catch (BufferUnderflowException | IOException e) {
            logger.warn("lose connection");
            //TODO 注销key
            message = null;
        } finally {
            Selector.getInstance().setKeyInterestOnRead(key);
        }
        return new MessageAndKey(message, key);
    }

    private byte[] readNByte(int length) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
//        byteBuffer.clear();
        byte[] bytes = new byte[length];
        socketChannel.read(byteBuffer);
        byteBuffer.flip();
        byteBuffer.get(bytes);
        return bytes;
    }
}
