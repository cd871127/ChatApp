package com.anthony.chatapp.client;

import com.anthony.chatapp.core.system.Parameter;

/**
 * Created by chend on 2017/7/11.
 */
public class ClientParameter extends Parameter {

    private ClientInfo clientInfo = new ClientInfo();
    private int clientPort;
    private String serverAddress;

    public static ClientParameter getInstance() {
        return (ClientParameter) instance;
    }

    private ClientParameter() {
        super();
        clientPort = Integer.valueOf(getProperties().getProperty("client_port"));
        serverAddress = getProperties().getProperty("server_address");
    }

    static {
        instance = new ClientParameter();
    }


    public int getClientPort() {
        return clientPort;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    @Override
    public String getSender() {
        return clientInfo.getUserId();
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public String getClientID() {
        return clientInfo.getUserId();
    }
}
