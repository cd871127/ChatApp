package com.anthony.chatapp.server.user;

import com.anthony.chatapp.core.service.AbstractService;
import com.anthony.chatapp.core.service.Task;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chend on 2017/6/28.
 */
public class UserLoginService extends AbstractService {
    public UserLoginService(int port) {
        super(port);
    }

    @Override
    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayList<Task> list = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket newSocket = serverSocket.accept();
                executor.submit(new UserLoginTask(newSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
