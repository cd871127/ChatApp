package com.anthony.chatapp.core.newcore.service;

import com.anthony.chatapp.core.newcore.Selector;
import com.anthony.chatapp.core.system.Parameter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 *
 */
public class ConnectionListenService extends AbstractService {
    private ServerSocketChannel serverSocketChannel;


    /**
     *
     */
    private ConnectionListenService() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(Parameter.getInstance().getListenPort()));
        } catch (IOException e) {
            logger.error("ConnectionHandleService constructor");
            e.printStackTrace();
        }
    }


    /**
     * @throws Exception .
     */
    @Override
    protected final void start() throws Exception {
        SocketChannel socketChannel;
        try {
            while (status) {
                socketChannel = serverSocketChannel.accept();
                logger.info("New connection from: " + socketChannel.getRemoteAddress().toString());
                Selector.getInstance().registChannel(socketChannel);
            }
        } catch (IOException e) {
            logger.error("ConnectionHandleService start");
            e.printStackTrace();
        }
    }

}
