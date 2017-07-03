package com.anthony.chatapp.core.service.receiver;

import com.anthony.chatapp.core.message.Message;
import com.anthony.chatapp.core.service.MessageReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

/**
 * Created by chend on 2017/6/30.
 */
public class MessageReceiver implements Callable<Message> {
    private static Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
    private SelectionKey key;
    private MessageReceiveService mrs;

    public MessageReceiver(SelectionKey key, MessageReceiveService mrs) {
        this.key = key;
        this.mrs = mrs;
    }

    private static int i = 0;

    @Override
    public Message call() {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        Message message;
        try {
            byte[] lengthByte = read(socketChannel, Message.HEADER_BYTE_COUNT);
            int bodyLength = Message.getMessageLength(lengthByte);
            byte[] bodyBytes = read(socketChannel, bodyLength);
            message = Message.decode(bodyBytes);
            //设置key对读感兴趣
            mrs.interestOps(key, key.interestOps() | SelectionKey.OP_READ);
        } catch (IOException e) {
            message = null;
            logger.warn("lose connection");
        }
        return message;
    }

    private byte[] read(SocketChannel socketChannel, int length) throws IOException {
        logger.debug("read length: " + length);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
        byteBuffer.clear();
        byte[] bytes = new byte[length];

        logger.debug("read from channel");
        socketChannel.read(byteBuffer);


        logger.debug(String.valueOf("position: " + byteBuffer.position()));
        byteBuffer.flip();
        byteBuffer.get(bytes);
        return bytes;
    }

}
