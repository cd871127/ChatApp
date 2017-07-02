package com.anthony.chatapp.server.service;

import com.anthony.chatapp.core.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/6/30.
 */
public class ConnectionService implements Runnable {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ServerSocketChannel serverSocketChannel;
    private MessageReceiveService mrs;

    public ConnectionService() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(Const.USER_LOGIN_PORT));
        mrs = MessageReceiveService.getInstance();
        logger.debug("ConnectionService created");
    }

    @Override
    public void run() {
        SocketChannel socketChannel;
        try {
            while (!Const.isShutdown) {
                socketChannel = serverSocketChannel.accept();
                logger.info("new connection: " + socketChannel.getRemoteAddress().toString());
                mrs.addChannel(socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
