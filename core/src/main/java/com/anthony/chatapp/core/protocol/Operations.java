package com.anthony.chatapp.core.protocol;

/**
 * Created by chend on 2017/6/29.
 */
public enum Operations{
    USER_LOGIN("login"), USER_LOGOUT("logout");

    private String value;

    Operations(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
