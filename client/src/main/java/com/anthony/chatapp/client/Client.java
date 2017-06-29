package com.anthony.chatapp.client;

import com.anthony.chatapp.core.protocol.TextMessage;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by chend on 2017/6/27.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        InetAddress localhost = InetAddress.getLocalHost();
        socket.connect(new InetSocketAddress(localhost, 51127));
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        String tmp;
//        PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
        TextMessage textMessage=new TextMessage();
        textMessage.setFrom("client");
        textMessage.setTo("server");
        while ((tmp = bufferedReader.readLine()) != null&&(!"quit".equals(tmp))) {
            textMessage.setTextBody(tmp);
            dos.write(textMessage.encode());
//            dos.flush();
        }
//        writer.close();
        dos.close();
        bufferedReader.close();
        socket.close();
    }
}
