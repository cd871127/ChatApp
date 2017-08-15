package com.anthony.chatapp.core.newcore.service;

import com.anthony.chatapp.core.newcore.Selector;
import com.anthony.chatapp.core.newcore.task.ParseMessageTask;

import java.nio.channels.SelectionKey;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chend on 2017/7/12.
 */
public class SelectorListenService extends AbstractService {
    private Selector selector = Selector.getInstance();
    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Override
    protected void start() throws Exception {
        while (status) {
            List<SelectionKey> keys = selector.readableKeys();
            keys.forEach((v) -> executorService.submit(new ParseMessageTask(v)));
        }
        executorService.shutdown();
    }
}
