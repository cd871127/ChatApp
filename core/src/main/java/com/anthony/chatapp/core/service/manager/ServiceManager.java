package com.anthony.chatapp.core.service.manager;

import com.anthony.chatapp.core.service.ConnectionService;
import com.anthony.chatapp.core.service.MessageDispatchService;
import com.anthony.chatapp.core.service.MessageReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chend on 2017/7/3.
 */
public class ServiceManager {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private int port;
    private MessageDispatchService mds;
    private MessageReceiveService mrs;
    private ConnectionService cs;

    public ServiceManager(int port) {
        this.port = port;
    }

    private void init() {
        mds = new MessageDispatchService();
        try {
            mrs = new MessageReceiveService(mds);
            cs = new ConnectionService(mrs, port);
        } catch (IOException e) {
            logger.debug("app start failed!");
            e.printStackTrace();
            return;
        }
    }

    public void startService() {
        init();
        ExecutorService es = Executors.newFixedThreadPool(3);
        es.submit(cs);
        es.submit(mrs);
        es.submit(mds);
        es.shutdown();
    }
}
