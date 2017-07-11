package com.anthony.chatapp.server;

import com.anthony.chatapp.core.system.Parameter;

/**
 * Created by chend on 2017/7/11.
 */
public class ServerParameter extends Parameter {

    private String server_name;

    public static ServerParameter getInstance() {
        return (ServerParameter) instance;
    }

    private ServerParameter() {
        super();
        server_name = getProperties().getProperty("server_name");
    }

    @Override
    public String getSender() {
        return server_name;
    }

    static {
        instance = new ServerParameter();
    }

    public String getServerName() {
        return server_name;
    }

}
