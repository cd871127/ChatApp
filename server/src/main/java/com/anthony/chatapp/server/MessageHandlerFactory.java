package com.anthony.chatapp.server;


import com.anthony.chatapp.core.protocol.message.Message;

/**
 * Created by chend on 2017/7/1.
 */
public class MessageHandlerFactory {
    public static MessageHandler getMessageHandler(Message.MessageTypes messageType) {
        MessageHandler messageHandler = null;
        switch (messageType) {
            case TEXT:
                messageHandler = new TextHandler();
                break;
            case FILE:
                messageHandler = new FileHandler();
                break;
            case OPERATION:
                messageHandler = new OperationHandler();
                break;
        }
        return messageHandler;
    }
}
