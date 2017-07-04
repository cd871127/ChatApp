package com.anthony.chatapp.client.message.hanlder;

import com.anthony.chatapp.client.UserController;
import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Operation;
import com.anthony.chatapp.core.message.handler.AbstractMessageHandler;

/**
 * Created by chend on 2017/7/4.
 */
public class OperationHandler extends AbstractMessageHandler {
    public OperationHandler(MessageAndKey messageAndKey) {
        super(messageAndKey);
    }

    @Override
    public void handle() {
        Message message = messageAndKey.getMessage();
        Operation.OperationTypes type = ((Operation) message.getAttachment()).getOperationType();
        switch (type) {
            case LOGIN:
                UserController.setIsLogin(true);
                break;
            case CONFIRM:
                break;
        }
    }
}
