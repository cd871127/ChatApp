package com.anthony.chatapp.core.service;

import java.net.Socket;

/**
 * Created by chend on 2017/6/28.
 */
public interface Task {
    void execute(Socket socket);
}
