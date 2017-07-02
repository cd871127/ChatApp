package com.anthony.chatapp.server.service;

import com.anthony.chatapp.core.protocol.message.Message;
import com.anthony.chatapp.server.handler.MessageHandler;
import com.anthony.chatapp.server.handler.factory.MessageHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by chend on 2017/7/1.
 */
public class MessageDispatchService implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(MessageDispatchService.class);

    private static MessageDispatchService instance = null;

    private MessageDispatchService() {
    }

    public static MessageDispatchService getInstance() {
        if (null == instance) {
            instance = new MessageDispatchService();
            logger.debug("MessageDispatchService created");
        }
        return instance;
    }

    private BlockingQueue<Future<Message>> futureList = new LinkedBlockingQueue<>();

    @Override
    public void run() {
        Future<Message> future = null;
        do {
            try {
                future = futureList.take();
                if (future.isDone()) {
                    Message message = future.get();
                    if (null != message) {
                        MessageHandler messageHandler = MessageHandlerFactory.getMessageHandler(message);
                        messageHandler.handle();
                    }
                } else {
                    addMessage(future);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } while (future != null);
    }


    public void addMessage(Future<Message> future) {
        try {
            futureList.put(future);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
