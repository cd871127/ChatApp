package com.anthony.chatapp.client.message.hanlder;

import com.anthony.chatapp.client.ClientMessageSender;
import com.anthony.chatapp.core.message.CachedMessageService;
import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Operation;
import com.anthony.chatapp.core.message.handler.AbstractMessageHandler;

/**
 * Created by chend on 2017/7/4.
 */
public class TextHandler extends AbstractMessageHandler {
    public TextHandler(MessageAndKey messageAndKey) {
        super(messageAndKey);
    }

    @Override
    public void handle() {
        System.out.println(messageAndKey.getMessage());
        Message message = new Message.MessageBuilder(Operation.OperationTypes.CONFIRM, messageAndKey.getMessage().getId(), "server").build();
        ClientMessageSender.getInstance().send(message);
        CachedMessageService.getInstance().addMessage(message.getId(),message);
    }
}
