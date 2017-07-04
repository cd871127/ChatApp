package com.anthony.chatapp.client;

import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Operation;

/**
 * Created by chend on 2017/7/3.
 */
public class UserController {
    private boolean isLogin = false;

    private ClientMessageSender clientMessageSender;

    public UserController(ClientMessageSender sender) {
        this.clientMessageSender = sender;
    }

    public void login() {
        Message message = new Message.MessageBuilder(Operation.OperationTypes.LOGIN, new ClientInfo(), "server").build();
        clientMessageSender.send(message);
    }


    public boolean logout() {
        return true;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
