package com.anthony.chatapp.server;


//import com.anthony.chatapp.core.protocol.MessageType;

import com.anthony.chatapp.server.service.ConnectionService;
import com.anthony.chatapp.server.service.MessageDispatchService;
import com.anthony.chatapp.server.service.MessageReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chend on 2017/6/27.
 */
public class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException {
        logger.info("Initialise Server:");
        ConnectionService cs = new ConnectionService();

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(cs);
        executorService.submit(MessageReceiveService.getInstance());
        executorService.submit(MessageDispatchService.getInstance());
        logger.info("Chat App start:");
        executorService.shutdown();

    }


}
