package com.anthony.chatapp.server.message.handler;

import com.anthony.chatapp.core.protocol.message.Message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/6/30.
 */
public class TextHandler extends ClientToClientMessageHandler {


    public TextHandler(Message message, SocketChannel sourceSocketChannel, SocketChannel targetSocketChannel) {
        super(message, sourceSocketChannel, targetSocketChannel);
    }

    @Override
    public void handle() {
        byte[] data = message.encode();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(data.length);
        byteBuffer.clear();
        byteBuffer.put(data);
        byteBuffer.flip();
        try {
            targetSocketChannel.write(byteBuffer);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }


}
