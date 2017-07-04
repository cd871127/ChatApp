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

    @Override
    public void startService() {
        super.startService();
        Thread t = new Thread(ServerMessageSenderService.getInstance());
        t.start();
    }
}
