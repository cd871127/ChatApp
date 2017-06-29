package com.anthony.chatapp.server;


import com.anthony.chatapp.core.Const;
import com.anthony.chatapp.server.user.UserLoginService;
import com.anthony.chatapp.server.user.UserLoginTask;

/**
 * Created by chend on 2017/6/27.
 */
public class Server {
    public static void main(String[] args) {
        UserLoginService userLoginService = new UserLoginService(new UserLoginTask(), Const.USER_LOGIN_PORT);
        userLoginService.start();
    }

}
