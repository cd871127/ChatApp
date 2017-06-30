package com.anthony.chatapp.server;

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
    public MessageDispatcherService mds;

    public ConnectionService() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
//        selector = Selector.open();
//        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(Const.USER_LOGIN_PORT));
//        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }


    @Override
    public void run() {
        SocketChannel socketChannel;
        try {
            while (!Const.isShutdown) {
                socketChannel = serverSocketChannel.accept();
                logger.debug("new connection");
                mds.addChannel(socketChannel);
                logger.debug("new connection2");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
