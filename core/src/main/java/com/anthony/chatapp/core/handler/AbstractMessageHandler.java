package com.anthony.chatapp.core.handler;

import com.anthony.chatapp.core.message.Message;

/**
 * Created by chend on 2017/7/1.
 */
public abstract class AbstractMessageHandler implements MessageHandler {
    protected Message message;

    public AbstractMessageHandler(Message message) {
        this.message = message;
    }
}
