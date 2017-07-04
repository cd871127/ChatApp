package com.anthony.chatapp.server;


//import com.anthony.chatapp.core.protocol.MessageType;

import com.anthony.chatapp.core.service.manager.ServiceManager;
import com.anthony.chatapp.core.system.Parameters;
import com.anthony.chatapp.server.handler.factory.ServerMessageHandlerFactory;
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
//        logger.info("Initialise Server:");
//        ConnectionService cs = new ConnectionService();
//
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        executorService.submit(cs);
//        executorService.submit(MessageReceiveService.getInstance());
//        executorService.submit(MessageDispatchService.getInstance());
//        logger.info("Chat App start:");
//        executorService.shutdown();
        ServiceManager sm = new ServerServiceManager(Parameters.SERVER_PORT, new ServerMessageHandlerFactory());
        sm.startService();

    }


}
