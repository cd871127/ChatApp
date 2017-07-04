package com.anthony.chatapp.server.handler.factory;


import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.handler.MessageHandler;
import com.anthony.chatapp.core.message.handler.factory.MessageHandlerFactory;
import com.anthony.chatapp.server.handler.FileHandler;
import com.anthony.chatapp.server.handler.OperationHandler;
import com.anthony.chatapp.server.handler.TextHandler;

/**
 * Created by chend on 2017/7/1.
 */
public class ServerMessageHandlerFactory extends MessageHandlerFactory {


    @Override
    protected MessageHandler textHandler(MessageAndKey messageAndKey) {
        return new TextHandler(messageAndKey);
    }

    @Override
    protected MessageHandler fileHandler(MessageAndKey messageAndKey) {
        return new FileHandler(messageAndKey);
    }

    @Override
    protected MessageHandler operationHandler(MessageAndKey messageAndKey) {
        return new OperationHandler(messageAndKey);
    }
}
