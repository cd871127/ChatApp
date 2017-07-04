package com.anthony.chatapp.client.message.hanlder.factory;

import com.anthony.chatapp.client.message.hanlder.OperationHandler;
import com.anthony.chatapp.client.message.hanlder.TextHandler;
import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.handler.MessageHandler;
import com.anthony.chatapp.core.message.handler.factory.MessageHandlerFactory;

/**
 * Created by chend on 2017/7/3.
 */
public class ClientMessageHandlerFactory extends MessageHandlerFactory {

    @Override
    protected MessageHandler textHandler(MessageAndKey messageAndKey) {
        return new TextHandler(messageAndKey);
    }

    @Override
    protected MessageHandler fileHandler(MessageAndKey messageAndKey) {
        return null;
    }

    @Override
    protected MessageHandler operationHandler(MessageAndKey messageAndKey) {
        return new OperationHandler(messageAndKey);
    }
}
