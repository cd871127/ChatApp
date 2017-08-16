package com.anthony.chatapp.client.message.hanlder;

import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
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
        Message message=messageAndKey.getMessage();
        sendAck(message);
        System.out.println(message);
    }
}
