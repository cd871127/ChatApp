package com.anthony.chatapp.core.message.handler.factory;

import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.handler.MessageHandler;

/**
 * Created by chend on 2017/7/3.
 */
public interface MessageHandlerFactory {
    MessageHandler getMessageHandler(MessageAndKey message);
}
