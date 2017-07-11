package com.anthony.chatapp.core.system;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by chend on 2017/6/30.
 */
public abstract class Parameter {

    protected static Parameter instance;

    private int server_port;
    private Properties properties;

    public static Parameter getInstance() {
        return instance;
    }

    protected Parameter() {
        readProperties();
        server_port = Integer.valueOf(properties.getProperty("server_port"));
    }

    //读取配置文件
    private void readProperties() {
//        try (InputStream in = new BufferedInputStream(new FileInputStream("setting.ini"))) {
        try {
            InputStream in = new BufferedInputStream(getClass().getResourceAsStream("/setting.ini"));
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getServerPort() {
        return server_port;
    }

    protected Properties getProperties() {
        return properties;
    }

    public abstract String getSender();
}
