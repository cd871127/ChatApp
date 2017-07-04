package com.anthony.chatapp.client;

import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Operation;
import com.anthony.chatapp.core.user.UserInfo;

/**
 * Created by chend on 2017/7/3.
 */
public class UserController {
    private static boolean isLogin = false;

    private ClientMessageSender clientMessageSender;

    public UserController(ClientMessageSender sender) {
        this.clientMessageSender = sender;
    }

    public boolean login() {
        Message message = new Message.MessageBuilder(Operation.OperationTypes.LOGIN,new ClientInfo(), "server").build();
        clientMessageSender.send(message);
        return true;
    }


    public boolean logout() {
        return true;
    }

    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        UserController.isLogin = isLogin;
    }
}
