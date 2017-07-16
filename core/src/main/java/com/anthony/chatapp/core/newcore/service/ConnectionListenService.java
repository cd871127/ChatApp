package com.anthony.chatapp.core.newcore.service;

import com.anthony.chatapp.core.Const;
import com.anthony.chatapp.core.newcore.Selector;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 *
 */
public abstract class ConnectionListenService extends AbstractService {
    private ServerSocketChannel serverSocketChannel;


    /**
     *
     */
    private ConnectionListenService() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            bindSocketToPort();
        } catch (IOException e) {
            logger.error("ConnectionHandleService constructor");
            e.printStackTrace();
        }
    }

    /**
     * subclass implement this method
     * invoke bind method of socket
     *
     * @throws IOException .
     */
    protected abstract void bindSocketToPort() throws IOException;

    /**
     * @throws Exception .
     */
    @Override
    protected final void start() throws Exception {
        SocketChannel socketChannel;
        try {
            while (!Const.isShutdown) {
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
