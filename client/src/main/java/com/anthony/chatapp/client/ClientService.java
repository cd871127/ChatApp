package com.anthony.chatapp.client;

import com.anthony.chatapp.client.message.hanlder.factory.ClientMessageHandlerFactory;
import com.anthony.chatapp.core.message.CachedMessageService;
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
    private ServiceManager serviceManager = new ServiceManager(Parameters.CLIENT_PORT, new ClientMessageHandlerFactory(ClientMessageSender.getInstance()));
    private UserController userController;

    private void init() {
        try {
            socketChannel = SocketChannel.open();
//            InetAddress localhost = InetAddress.getLocalHost();
            InetAddress server = InetAddress.getLocalHost();
//            InetAddress server = InetAddress.getByName("192.168.60.1");
//            InetAddress server = InetAddress.getByName("45.78.44.179");
            socketChannel.connect(new InetSocketAddress(server, Parameters.SERVER_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!socketChannel.isConnected()) {
            logger.info("connect to server");
        }

        serviceManager.addService(CachedMessageService.getInstance(ClientMessageSender.getInstance()));

        serviceManager.startService();
        SelectionKey key = serviceManager.addChannel(socketChannel);
        ClientMessageSender.getInstance().setKey(key);

        ClientInfo.setLocalUserId(Parameters.SENDER);
        //userController 要在sender后面
        userController = new UserController(ClientMessageSender.getInstance());
    }

    public void start() {
        init();
        userController.login();

    }
}
