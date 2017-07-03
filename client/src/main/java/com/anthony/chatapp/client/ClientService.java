package com.anthony.chatapp.client;

import com.anthony.chatapp.client.message.hanlder.factory.ClientMessageHandlerFactory;
import com.anthony.chatapp.core.service.manager.ServiceManager;
import com.anthony.chatapp.core.system.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/7/3.
 */
public class ClientService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SocketChannel socketChannel;
    private ServiceManager serviceManager = new ServiceManager(Parameters.CLIENT_PORT, new ClientMessageHandlerFactory());
    private UserController userController;

    private void init() {
        try {
            socketChannel = SocketChannel.open();
            InetAddress localhost = InetAddress.getLocalHost();
            socketChannel.connect(new InetSocketAddress(localhost, Parameters.SERVER_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!socketChannel.isConnected()) {
            logger.info("connect to server");
        }
        serviceManager.startService();
        SelectionKey key = serviceManager.addChannel(socketChannel);

        ClientInfo.setLocalUserId(Parameters.SENDER);
        //userController 要在sender后面
        userController = new UserController(ClientMessageSender.getInstance(key));
    }

    public void start() {
        init();
        boolean isLogin=userController.login();
        userController.setIsLogin(isLogin);
    }
}
