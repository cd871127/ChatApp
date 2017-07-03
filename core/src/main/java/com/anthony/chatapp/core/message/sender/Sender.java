package com.anthony.chatapp.core.message.sender;

import com.anthony.chatapp.core.message.entity.Message;

/**
 * Created by chend on 2017/7/3.
 */
public interface Sender {
    int send(Message message);
}
