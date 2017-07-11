package com.anthony.chatapp.server;



import com.anthony.chatapp.core.service.manager.ServiceManager;
import com.anthony.chatapp.server.handler.factory.ServerMessageHandlerFactory;
import com.anthony.chatapp.server.service.ServerMessageSenderService;
import com.anthony.chatapp.server.service.manager.ServerServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by chend on 2017/6/27.
 */
public class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException {
//        Parameters.SENDER="server";
        ServiceManager sm = new ServerServiceManager(ServerParameter.getInstance().getServerPort(), new ServerMessageHandlerFactory(ServerMessageSenderService.getInstance()));
        sm.startService();
        logger.info("Chat App server start");

    }


}
