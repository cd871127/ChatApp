package com.anthony.chatapp.client;

import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Operation;
import com.anthony.chatapp.core.system.Parameter;

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
        Operation login=new Operation(Operation.OperationType.LOGIN, ClientParameter.getInstance().getClientInfo(),"server");
        clientMessageSender.send(login);
//        CachedMessageService.getInstance().addMessage(message.getId(),message);
    }


    public void logout() {
        Operation login=new Operation(Operation.OperationType.LOGOUT,ClientParameter.getInstance().getClientInfo(),"server");
        clientMessageSender.send(login);
        setIsLogin(false);
    }

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        UserController.isLogin = isLogin;
    }
}
