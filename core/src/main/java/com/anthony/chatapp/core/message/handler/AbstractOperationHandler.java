package com.anthony.chatapp.core.message.handler;

import com.anthony.chatapp.core.message.CachedMessageService;
import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Operation;

import java.nio.file.OpenOption;

/**
 * Created by chend on 2017/7/4.
 */
public abstract class AbstractOperationHandler extends AbstractMessageHandler {

    public AbstractOperationHandler(MessageAndKey messageAndKey) {
        super(messageAndKey);
    }

    protected void sendAckAck(Message ack) {
        CachedMessageService.getInstance().removeMessage(((Operation)ack).getAttachment().toString());
        Message ackAck = new Operation(Operation.OperationType.ACKACK, ack.getId(), ack.getSender());
        sender.send(ackAck);
    }


}
