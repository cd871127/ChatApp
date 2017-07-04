package com.anthony.chatapp.client;

import com.anthony.chatapp.core.user.UserInfo;

/**
 * Created by chend on 2017/7/3.
 */
public class ClientInfo extends UserInfo {
    private String password;

    public static String getLocalUserId() {
        return localUserId;
    }

    public static void setLocalUserId(String localUserId) {
        ClientInfo.localUserId = localUserId;
    }

    private static String localUserId;

    public ClientInfo() {
        setUserId(localUserId);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
