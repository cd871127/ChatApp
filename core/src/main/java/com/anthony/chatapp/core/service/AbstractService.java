package com.anthony.chatapp.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by chend on 2017/6/28.
 */
public class AbstractService implements Service {

    private Logger logger= LoggerFactory.getLogger(getClass());

    private Task task;
    private int port;

    public AbstractService(Task task, int port) {
        this.task = task;
        this.port = port;
    }

    @Override
    public void start() {
        logger.info("service start");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.debug("new connection: "+serverSocket.getInetAddress().getHostAddress());
            while (true) {
                try (Socket newSocket = serverSocket.accept()) {
                    task.execute(newSocket);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
