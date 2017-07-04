package com.anthony.chatapp.client;

import com.anthony.chatapp.core.message.CachedMessageService;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.system.Parameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by chend on 2017/6/27.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
        if (args.length == 0) {
//            Scanner scanner = new Scanner(System.in);
            System.out.println("input your name:");
//            Parameters.SENDER = scanner.nextLine();
            Parameters.SENDER=reader.readLine();
        }

//        Parameters.SENDER="111";///////////////////

        ClientService clientService = new ClientService();
        clientService.start();

        if (!UserController.isLogin()) {
            System.out.println("login ");
        }
        System.out.println("login ok");

        String line;
        /**command ui**/
        while ((line=reader.readLine())!=null) {
            String[] tmp = line.split(":");
//        int i=0;
//        while (true){/////////////
//            String[] tmp = (i+"sss:234234").split(":");///////////////////
            Message message = new Message.MessageBuilder(tmp[1], tmp[0]).build();
            ClientMessageSender.getInstance().send(message);
            CachedMessageService.getInstance().addMessage(message.getId(),message);
        }
    }

}
