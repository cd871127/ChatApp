package com.anthony.chatapp.core.protocol.processor.sender;

import com.anthony.chatapp.core.protocol.message.Message;

/**
 * Created by chend on 2017/6/30.
 */
public interface MessageSender {
    void send(Message message);
}
