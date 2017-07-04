package com.anthony.chatapp.core.service.manager;

import com.anthony.chatapp.core.message.CachedMessageService;
import com.anthony.chatapp.core.message.handler.factory.MessageHandlerFactory;
import com.anthony.chatapp.core.service.ConnectionService;
import com.anthony.chatapp.core.service.MessageDispatchService;
import com.anthony.chatapp.core.service.MessageReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
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
    private MessageHandlerFactory mdf;
    private CachedMessageService cms;

    public ServiceManager(int port, MessageHandlerFactory mdf) {
        this.port = port;
        this.mdf = mdf;
    }

    private boolean init() {
        mds = new MessageDispatchService(mdf);
        try {
            mrs = new MessageReceiveService(mds);
            cs = new ConnectionService(mrs, port);
        } catch (IOException e) {
            logger.debug("app start failed!");
            e.printStackTrace();
            return false;
        }
        cms = CachedMessageService.getInstance();
        return true;
    }

    public void startService() {
        if (!init())
            return;
        ExecutorService es = Executors.newFixedThreadPool(4);

        es.submit(cs);
        es.submit(mrs);
        es.submit(mds);
        es.submit(cms);
        es.shutdown();
    }

    public SelectionKey addChannel(SocketChannel socketChannel) {
        return mrs.addChannel(socketChannel);
    }
}
