package com.anthony.chatapp.core.protocol;

/**
 * Created by chend on 2017/6/29.
 */
public enum MessageTypes {
    TEXT("text"), VIDEO("video"), IMAGE("image"), AUDIO("audio"), OPERATION("operation");

    private String value;

    MessageTypes(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
