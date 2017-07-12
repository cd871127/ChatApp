package com.anthony.chatapp.core.newcore.service;

import com.anthony.chatapp.core.Const;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/7/12.
 */
public abstract class ConnectionListenService extends AbstractService {

    private ServerSocketChannel serverSocketChannel;
//    private Selector selector;

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

    @Override
    protected final void start() throws Exception {
        SocketChannel socketChannel;
        try {
            while (!Const.isShutdown) {
                socketChannel = serverSocketChannel.accept();
                logger.info("New connection from: " + socketChannel.getRemoteAddress().toString());
//                mrs.addChannel(socketChannel);
            }
        } catch (IOException e) {
            logger.error("ConnectionHandleService start");
            e.printStackTrace();
        }
    }

}
