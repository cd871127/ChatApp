package com.anthony.chatapp.client.message.hanlder.factory;

import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.handler.MessageHandler;
import com.anthony.chatapp.core.message.handler.factory.MessageHandlerFactory;

/**
 * Created by chend on 2017/7/3.
 */
public class ClientMessageHandlerFactory implements MessageHandlerFactory {
    @Override
    public MessageHandler getMessageHandler(MessageAndKey messageAndKey) {
        System.out.println(messageAndKey.getMessage());
        return null;
    }
}
