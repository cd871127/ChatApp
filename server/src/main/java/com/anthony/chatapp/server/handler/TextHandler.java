package com.anthony.chatapp.server.handler;

import com.anthony.chatapp.core.handler.AbstractMessageHandler;
import com.anthony.chatapp.core.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chend on 2017/7/1.
 */
public class TextHandler extends AbstractMessageHandler {
    private static Logger logger = LoggerFactory.getLogger(TextHandler.class);

    public TextHandler(Message message) {
        super(message);
    }

    @Override
    public void handle() {
        logger.debug(message.toString());
    }
}
