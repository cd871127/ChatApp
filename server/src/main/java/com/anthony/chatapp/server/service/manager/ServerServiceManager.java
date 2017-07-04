package com.anthony.chatapp.server.service.manager;

import com.anthony.chatapp.core.message.handler.factory.MessageHandlerFactory;
import com.anthony.chatapp.core.service.manager.ServiceManager;
import com.anthony.chatapp.server.service.ServerMessageSenderService;

/**
 * Created by chend on 2017/7/4.
 */
public class ServerServiceManager extends ServiceManager {
    public ServerServiceManager(int port, MessageHandlerFactory mdf) {
        super(port, mdf);
    }

    private ServerMessageSenderService serverMessageSenderService;

    @Override
    public void startService() {
        super.startService();
        serverMessageSenderService = ServerMessageSenderService.getInstance();
        Thread t = new Thread(serverMessageSenderService);
        t.start();
    }
}
