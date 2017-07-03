package com.anthony.chatapp.server.handler.factory;


import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.handler.MessageHandler;
import com.anthony.chatapp.core.message.handler.factory.MessageHandlerFactory;
import com.anthony.chatapp.server.handler.FileHandler;
import com.anthony.chatapp.server.handler.OperationHandler;
import com.anthony.chatapp.server.handler.TextHandler;

/**
 * Created by chend on 2017/7/1.
 */
public class ServerMessageHandlerFactory implements MessageHandlerFactory {

    public MessageHandler getMessageHandler(MessageAndKey messageAndKey) {
        MessageHandler messageHandler = null;
        Message message = messageAndKey.getMessage();
        Message.MessageTypes messageType = message.getType();
        switch (messageType) {
            case TEXT:
                messageHandler = new TextHandler(messageAndKey);
                break;
            case FILE:
                messageHandler = new FileHandler(messageAndKey);
                break;
            case OPERATION:
                messageHandler = new OperationHandler(messageAndKey);
                break;
        }
        return messageHandler;
    }
}
