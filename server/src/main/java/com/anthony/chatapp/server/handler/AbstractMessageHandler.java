package com.anthony.chatapp.server.handler;

import com.anthony.chatapp.core.protocol.message.Message;

/**
 * Created by chend on 2017/7/1.
 */
public abstract class AbstractMessageHandler implements MessageHandler {
    protected Message message;

    AbstractMessageHandler(Message message) {
        this.message = message;
    }
}
