package com.anthony.chatapp.server;


//import com.anthony.chatapp.core.protocol.MessageType;

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
//        UserLoginService userLoginService = new UserLoginService(Const.USER_LOGIN_PORT);
//        System.out.println(Runtime.getRuntime().availableProcessors());
//        userLoginService.start();
        logger.debug("app start");
        ConnectionService cs = new ConnectionService();
        MessageDispatcherService mds=new MessageDispatcherService();
        cs.mds=mds;
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(cs);
        executorService.submit(mds);
        executorService.shutdown();
//        logger.debug("executorService shutdown ");

    }


}
