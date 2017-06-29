package com.anthony.chatapp.server.user;

import com.anthony.chatapp.core.service.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by chend on 2017/6/28.
 */
public class UserLoginTask implements Task {
    @Override
    public void execute(Socket socket) {
        UserContainer container=UserContainer.getInstance();
        try {
            InputStream in=socket.getInputStream();
            Scanner scanner=new Scanner(in);
            while (scanner.hasNext())
                System.out.println(scanner.next());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
