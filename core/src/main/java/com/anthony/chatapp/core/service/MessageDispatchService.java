package com.anthony.chatapp.core.service;

import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Operation;
import com.anthony.chatapp.core.message.handler.MessageHandler;
import com.anthony.chatapp.core.message.handler.factory.MessageHandlerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by chend on 2017/7/1.
 * 根据不同的消息类型,分配不同的处理器
 */
public class MessageDispatchService extends Service {

    private MessageHandlerFactory mdf;

    public MessageDispatchService(MessageHandlerFactory mdf) {
        this.mdf = mdf;
    }

    private BlockingQueue<Future<MessageAndKey>> futureList = new LinkedBlockingQueue<>();

    @Override
    public void run() {
        Future<MessageAndKey> future = null;
        do {
            try {
                future = futureList.take();
                if (future.isDone()) {
                    MessageAndKey messageAndKey = future.get();
                    if (null == messageAndKey.getMessage()) {
                        messageAndKey.setMessage(new Operation(Operation.OperationType.LC, "server"));
                    }
                    MessageHandler messageHandler = mdf.getMessageHandler(messageAndKey);
                    logger.debug("***********************************");
                    logger.debug(messageAndKey.getMessage().toString());
                    logger.debug("***********************************");
                    if (messageHandler != null) {
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


    public void addMessage(Future<MessageAndKey> future) {
        try {
            futureList.put(future);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
