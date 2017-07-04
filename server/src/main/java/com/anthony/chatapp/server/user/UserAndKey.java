package com.anthony.chatapp.server.user;

import com.anthony.chatapp.core.user.UserInfo;

import java.nio.channels.SelectionKey;

/**
 * Created by chend on 2017/7/4.
 */
public class UserAndKey {
    private UserInfo userInfo;
    private SelectionKey key;

    public UserAndKey() {
    }

    public UserAndKey(UserInfo userInfo, SelectionKey key) {
        this.userInfo = userInfo;
        this.key = key;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public SelectionKey getKey() {
        return key;
    }

    public void setKey(SelectionKey key) {
        this.key = key;
    }
}
