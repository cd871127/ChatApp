package com.anthony.chatapp.core.message.handler;

import com.anthony.chatapp.core.message.MessageAndKey;

/**
 * Created by chend on 2017/7/1.
 */
public abstract class AbstractMessageHandler implements MessageHandler {
    protected MessageAndKey messageAndKey;

    public AbstractMessageHandler(MessageAndKey messageAndKey) {
        this.messageAndKey = messageAndKey;
    }
}
