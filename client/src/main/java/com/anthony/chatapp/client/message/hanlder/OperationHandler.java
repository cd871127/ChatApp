package com.anthony.chatapp.client.message.hanlder;

import com.anthony.chatapp.client.UserController;
import com.anthony.chatapp.core.message.CachedMessageService;
import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Operation;
import com.anthony.chatapp.core.message.handler.AbstractOperationHandler;

/**
 * Created by chend on 2017/7/4.
 * 1
 */
public class OperationHandler extends AbstractOperationHandler {
    public OperationHandler(MessageAndKey messageAndKey) {
        super(messageAndKey);
    }

    @Override
    public void handle() {
        Message message = messageAndKey.getMessage();
        Operation.OperationType type = ((Operation) message).getOperationType();
        switch (type) {
            case LOGIN:
                UserController.setIsLogin(true);
//                CachedMessageService.getInstance().removeMessage(((Operation) (message.getAttachment())).getAttachment().toString());
                break;
            case ACK:
                sendAckAck(message);
                break;
            case ACKACK:
                CachedMessageService.getInstance().removeMessage(((Operation) message).getAttachment().toString());
                break;
        }
    }
}
