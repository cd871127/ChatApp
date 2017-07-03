package com.anthony.chatapp.core.message.handler.factory;

import com.anthony.chatapp.core.message.handler.MessageHandler;
import com.anthony.chatapp.core.message.entity.Message;

/**
 * Created by chend on 2017/7/3.
 */
public interface MessageHandlerFactory {
    MessageHandler getMessageHandler(Message message);
}
