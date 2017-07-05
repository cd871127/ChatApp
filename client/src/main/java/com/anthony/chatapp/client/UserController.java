package com.anthony.chatapp.client;

import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Operation;

/**
 * Created by chend on 2017/7/3.
 */
public class UserController {
    private static volatile boolean isLogin;

    private ClientMessageSender clientMessageSender;

    public UserController(ClientMessageSender sender) {
        this.clientMessageSender = sender;
    }

    public void login() {
        Message message = new Message.MessageBuilder(Operation.OperationTypes.LOGIN, new ClientInfo(), "server").build();
        clientMessageSender.send(message);
//        CachedMessageService.getInstance().addMessage(message.getId(),message);
    }


    public void logout() {
        Message message = new Message.MessageBuilder(Operation.OperationTypes.LOGOUT, new ClientInfo(), "server").build();
        clientMessageSender.send(message);
        setIsLogin(false);
    }

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        UserController.isLogin = isLogin;
    }
}
