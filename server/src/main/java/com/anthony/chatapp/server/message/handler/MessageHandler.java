package com.anthony.chatapp.server.message.handler;

/**
 * Created by chend on 2017/6/30.
 */
public interface MessageHandler extends Runnable{
    void handle();
}
