package com.anthony.chatapp.core.message.handler;

import com.anthony.chatapp.core.message.CachedMessageService;
import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Operation;
import com.anthony.chatapp.core.message.sender.Sender;

/**
 * Created by chend on 2017/7/1.
 */
public abstract class AbstractMessageHandler implements MessageHandler {
    protected MessageAndKey messageAndKey;
    protected static Sender sender;

    public AbstractMessageHandler(MessageAndKey messageAndKey) {
        this.messageAndKey = messageAndKey;
    }

    protected void sendAck(Message originMessage) {

        Message ack = new Message.MessageBuilder(Operation.OperationTypes.ACK, originMessage.getId(), originMessage.getSender()).build();
        sender.send(ack);
        CachedMessageService.getInstance().addMessage(ack.getId(), ack);
    }

    public static void setSender(Sender sender) {
        AbstractMessageHandler.sender = sender;
    }
}
