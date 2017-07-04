package com.anthony.chatapp.core.message.handler.factory;

import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.handler.AbstractMessageHandler;
import com.anthony.chatapp.core.message.handler.MessageHandler;
import com.anthony.chatapp.core.message.sender.Sender;

/**
 * Created by chend on 2017/7/3.
 */
public abstract class MessageHandlerFactory {
    public MessageHandlerFactory(Sender sender) {
        AbstractMessageHandler.setSender(sender);
    }

    public MessageHandler getMessageHandler(MessageAndKey messageAndKey) {
        MessageHandler messageHandler = null;
        Message message = messageAndKey.getMessage();
        Message.MessageTypes messageType = message.getType();
        switch (messageType) {
            case TEXT:
                messageHandler = textHandler(messageAndKey);
                break;
            case FILE:
                messageHandler = fileHandler(messageAndKey);
                break;
            case OPERATION:
                messageHandler = operationHandler(messageAndKey);
                break;
        }
        return messageHandler;
    }

    protected abstract MessageHandler textHandler(MessageAndKey messageAndKey);

    protected abstract MessageHandler fileHandler(MessageAndKey messageAndKey);

    protected abstract MessageHandler operationHandler(MessageAndKey messageAndKey);
}
