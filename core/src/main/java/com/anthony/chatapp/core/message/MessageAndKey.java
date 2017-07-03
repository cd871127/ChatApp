package com.anthony.chatapp.core.message;

import com.anthony.chatapp.core.message.entity.Message;

import java.nio.channels.SelectionKey;

/**
 * Created by chend on 2017/7/4.
 */
public class MessageAndKey {
    private Message message;
    private SelectionKey key;

    public MessageAndKey(Message message, SelectionKey key) {
        this.message = message;
        this.key = key;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public SelectionKey getKey() {
        return key;
    }

    public void setKey(SelectionKey key) {
        this.key = key;
    }
}
