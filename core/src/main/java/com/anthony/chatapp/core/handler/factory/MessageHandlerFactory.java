package com.anthony.chatapp.core.handler.factory;

import com.anthony.chatapp.core.handler.MessageHandler;
import com.anthony.chatapp.core.message.Message;

/**
 * Created by chend on 2017/7/3.
 */
public interface MessageHandlerFactory {
    static MessageHandler getMessageHandler(Message message) {
        return null;
    }
}
