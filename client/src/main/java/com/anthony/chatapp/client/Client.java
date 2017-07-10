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
        String[] tmp = "sss:1111".split(":");

        Message message = new Text(tmp[1], tmp[0]);
        ClientMessageSender.getInstance().send(message);
        CachedMessageService.getInstance().addMessage(message.getId(), message);
    }

    private static void userInput(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if (args.length == 0) {
//            Scanner scanner = new Scanner(System.in);
            System.out.println("input your name:");
//            Parameters.SENDER = scanner.nextLine();
            Parameters.SENDER = reader.readLine();
        }

//        Parameters.SENDER="111";///////////////////

        ClientService clientService = new ClientService();
        clientService.start();
//
//        if (!UserController.isLogin()) {
//            System.out.println("login ");
//        }
        System.out.println("login ok");

        String line;
        /**command ui**/
        while ((line = reader.readLine()) != null) {
            String[] tmp = line.split(":");
//        int i=0;
//        while (true){/////////////
//            String[] tmp = (i+"sss:234234").split(":");///////////////////
            Message message = new Text(tmp[1], tmp[0]);
            ClientMessageSender.getInstance().send(message);
            CachedMessageService.getInstance().addMessage(message.getId(), message);
        }
    }

}
