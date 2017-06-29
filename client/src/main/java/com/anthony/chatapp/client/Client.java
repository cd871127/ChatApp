package com.anthony.chatapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by chend on 2017/6/27.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("Client");
        URL url = new URL("http://www.baidu.com");
        URLConnection connection = url.openConnection();
        connection.connect();
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        headerFields.forEach((k, v) -> {
            System.out.println(k + ": ");
            v.forEach((t)-> System.out.println("    "+t));
        });

        System.out.println(connection.getContentType());

        BufferedReader bf=new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String tmp;
        while ((tmp=bf.readLine())!=null)
            System.out.println(tmp);
    }
}
