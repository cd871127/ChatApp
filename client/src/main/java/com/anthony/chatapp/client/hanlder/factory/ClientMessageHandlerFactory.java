package com.anthony.chatapp.client.hanlder.factory;

import com.anthony.chatapp.core.message.handler.MessageHandler;
import com.anthony.chatapp.core.message.handler.factory.MessageHandlerFactory;
import com.anthony.chatapp.core.message.entity.Message;

/**
 * Created by chend on 2017/7/3.
 */
public class ClientMessageHandlerFactory implements MessageHandlerFactory {
    @Override
    public MessageHandler getMessageHandler(Message message) {
        return null;
    }
}
