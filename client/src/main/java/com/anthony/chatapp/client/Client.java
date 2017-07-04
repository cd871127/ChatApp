package com.anthony.chatapp.client;

import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.system.Parameters;

import java.util.Scanner;

/**
 * Created by chend on 2017/6/27.
 */
public class Client {

    public static void main(String[] args) {
        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("input your name:");
            Parameters.SENDER = scanner.nextLine();
        }

//        Parameters.SENDER="111";///////////////////

        ClientService clientService = new ClientService();
        clientService.start();

        if (!UserController.isIsLogin()) {
            System.out.println("log error");
            return;
        }
        /**command ui**/
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String[] tmp = scanner.nextLine().split(":");
//        int i=0;
//        while (true){/////////////
//            String[] tmp = (i+"sss:234234").split(":");///////////////////
            Message message = new Message.MessageBuilder(tmp[1], tmp[0]).build();
            ClientMessageSender.getInstance().send(message);
        }
    }

}
