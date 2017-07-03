package com.anthony.chatapp.server.handler.factory;


import com.anthony.chatapp.core.message.handler.MessageHandler;
import com.anthony.chatapp.core.message.handler.factory.MessageHandlerFactory;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.server.handler.FileHandler;
import com.anthony.chatapp.server.handler.OperationHandler;
import com.anthony.chatapp.server.handler.TextHandler;

/**
 * Created by chend on 2017/7/1.
 */
public class ServerMessageHandlerFactory implements MessageHandlerFactory {

    public MessageHandler getMessageHandler(Message message) {
        MessageHandler messageHandler = null;
        Message.MessageTypes messageType = message.getType();
        switch (messageType) {
            case TEXT:
                messageHandler = new TextHandler(message);
                break;
            case FILE:
                messageHandler = new FileHandler(message);
                break;
            case OPERATION:
                messageHandler = new OperationHandler(message);
                break;
        }
        return messageHandler;
    }
}
