package com.anthony.chatapp.server;

import com.anthony.chatapp.core.protocol.message.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by chend on 2017/7/1.
 */
public class MessageDispatchService implements Runnable {

    private BlockingQueue<Future<Message>> futureList = new LinkedBlockingQueue<>();

    @Override
    public void run() {
        Future<Message> future = null;
        do {
            try {
                future = futureList.take();
                if (!future.isDone()) {
                    addMessage(future);
                    continue;
                }
                Message message = future.get();
                MessageHandler messageHandler = MessageHandlerFactory.getMessageHandler(message.getType());
                System.out.println(message);
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
