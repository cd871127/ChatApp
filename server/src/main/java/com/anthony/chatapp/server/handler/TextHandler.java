package com.anthony.chatapp.server.handler;

import com.anthony.chatapp.core.message.CachedMessage;
import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.handler.AbstractMessageHandler;
import com.anthony.chatapp.server.service.ServerMessageSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chend on 2017/7/1.
 */
public class TextHandler extends AbstractMessageHandler {
    private static Logger logger = LoggerFactory.getLogger(TextHandler.class);

    public TextHandler(MessageAndKey messageAndKey) {
        super(messageAndKey);
    }

    @Override
    public void handle() {
        Message message = messageAndKey.getMessage();

        //写入到消息队列中 消息队列自己发送消息
        CachedMessage.getInstance().addMessage(message.getId(), message);

        ServerMessageSenderService.getInstance().send(message);
        System.out.println(messageAndKey.getMessage());
    }


}
