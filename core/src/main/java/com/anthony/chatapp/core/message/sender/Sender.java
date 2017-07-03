package com.anthony.chatapp.core.message.sender;

import com.anthony.chatapp.core.message.entity.Message;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/7/3.
 */
public interface Sender {
    int send(Message message, SocketChannel socketChannel) throws IOException;
}
