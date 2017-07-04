package com.anthony.chatapp.server.service;

import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.sender.MessageSender;
import com.anthony.chatapp.server.user.UserManager;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by chend on 2017/7/4.
 * 维护一个消息队列
 * 发送里面的消息
 * 只有一个写线程,不需要对key同步
 */
public class ServerMessageSenderService extends MessageSender implements Runnable {
    private static ServerMessageSenderService ourInstance = new ServerMessageSenderService();

    public static ServerMessageSenderService getInstance() {
        return ourInstance;
    }

    private ServerMessageSenderService() {
    }

    private BlockingQueue<Message> queue = new LinkedBlockingQueue<>();

    @Override
    public void run() {
        Message message;
        try {
            while ((message = queue.take()) != null) {
                //获得receiver的key
                SelectionKey key = UserManager.getInstance().getUserSelectionKey(message.getReceiver());
                if (key != null)
                    messageIntoSocketChannel(message, (SocketChannel) key.channel());
                else
                    send(message);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int send(Message message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return queue.size();
    }
}
