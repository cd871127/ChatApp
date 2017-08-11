package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.handler.SelectionKeyHandler;
import anthony.libs.chatapp.core.manager.SelectorManager;
import anthony.libs.chatapp.core.service.AbstractService;

import java.nio.channels.SelectionKey;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chend on 2017/8/10.
 */
public class MessageListener extends AbstractService {
    private static MessageListener ourInstance = new MessageListener();

    public static MessageListener getInstance() {
        return ourInstance;
    }

    private ExecutorService es;

    private MessageListener() {
        super();
        this.es = Executors.newFixedThreadPool(3);
    }


    @Override
    protected void execute() {
        while (getStatus()) {
            Set<SelectionKey> readableKeys = SelectorManager.getInstance().getReadableKeys();
            if (null != readableKeys)
                readableKeys.forEach((v) -> {
                    //开新线程处理消息
                    getLogger().debug("new message");
                    es.submit(new SelectionKeyHandler(v));
                });
        }
    }
}
