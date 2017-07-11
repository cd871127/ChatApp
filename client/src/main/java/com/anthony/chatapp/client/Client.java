package com.anthony.chatapp.client;

import com.anthony.chatapp.core.message.CachedMessageService;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Text;
import com.anthony.chatapp.core.system.Parameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by chend on 2017/6/27.
 */
public class Client {

    public static void main(String[] args) throws IOException {
//        debug();
        userInput(args);
    }


    private static void debug() {
        Parameters.SENDER = "debug";

        ClientService clientService = new ClientService();
        clientService.start();


        System.out.println("logging... ");
        boolean flag = false;
//        while (!flag) {
//            flag = UserController.isLogin();
//        }
        while (!UserController.isLogin())
            ;
        System.out.println("login success");


        /**command ui**/
//        String[] tmp = "debug:1111".split(":");
//
//        Message message = new Text(tmp[1], tmp[0]);
//        ClientMessageSender.getInstance().send(message);
//        CachedMessageService.getInstance().addMessage(message.getId(), message);
    }

    private static void userInput(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if (args.length == 0) {
            System.out.println("input your name:");
            Parameters.SENDER = reader.readLine();
        }

        ClientService clientService = new ClientService();
        clientService.start();

        System.out.println("login success");

        String line;
        /**command ui**/
        while ((line = reader.readLine()) != null) {
            String[] tmp = line.split(":");
            Message message = new Text(tmp[1], tmp[0]);
            ClientMessageSender.getInstance().send(message);
            CachedMessageService.getInstance().addMessage(message.getId(), message);
        }
    }

}
