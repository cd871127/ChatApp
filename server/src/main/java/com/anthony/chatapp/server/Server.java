package com.anthony.chatapp.server;


//import com.anthony.chatapp.core.protocol.MessageType;

import com.anthony.chatapp.core.Const;
import com.anthony.chatapp.server.user.UserLoginService;

/**
 * Created by chend on 2017/6/27.
 */
public class Server {
    public static void main(String[] args) {
        UserLoginService userLoginService = new UserLoginService(Const.USER_LOGIN_PORT);
        System.out.println(Runtime.getRuntime().availableProcessors());
        userLoginService.start();
//        System.out.println(MessageType.USER_LOGOUT);
    }

}
