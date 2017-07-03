package com.anthony.chatapp.core.user;

import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/7/3.
 */
public class UserInfo {
    private String userId;
    private String userName;
    private SocketChannel socketChannel;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
