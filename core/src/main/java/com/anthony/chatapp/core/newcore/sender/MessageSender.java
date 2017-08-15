package com.anthony.chatapp.core.newcore.sender;

import com.anthony.chatapp.core.message.entity.Message;

/**
 * Created by chend on 2017/7/16.
 */
public interface MessageSender {
    int send(Message message);
}
