package com.anthony.chatapp.server.handler;

import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.handler.AbstractMessageHandler;
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
//        SocketChannel socketChannel = UserManager.getInstance().getUserChannel("");
//        try {
//            new MessageSender().send(message, socketChannel);
//        } catch (IOException e) {
//            logger.debug("send to " + message.getReceiver() + " failed");
//            e.printStackTrace();
//        }
        System.out.println(message);
    }
}
