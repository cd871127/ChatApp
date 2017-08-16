package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.container.MessageAndKeyContainer;
import anthony.libs.chatapp.core.handler.ReadableSelectionKeyHandler;
import anthony.libs.chatapp.core.manager.ConnectionManager;
import anthony.libs.chatapp.core.service.AbstractService;

import java.nio.channels.SelectionKey;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chend on 2017/8/10.
 * 监听新的消息
 */
public class MessageListener extends AbstractService {
    private static MessageListener ourInstance = new MessageListener();

    public static MessageListener getInstance() {
        return ourInstance;
    }

    private ExecutorService es;
    private ConnectionManager connectionManager;
    private MessageAndKeyContainer messageAndKeyContainer;

    private MessageListener() {
        super();
        this.es = Executors.newFixedThreadPool(3);
        this.connectionManager = ConnectionManager.getInstance();
        this.messageAndKeyContainer = MessageAndKeyContainer.getInstance();
    }


    @Override
    protected void execute() {
        while (getStatus()) {
            Set<SelectionKey> readableKeys = connectionManager.getReadableKeys();
            if (null != readableKeys)
                readableKeys.forEach((v) -> {
                    //开新线程处理消息
                    getLogger().debug("new message");
                    messageAndKeyContainer.addFuture(es.submit(new ReadableSelectionKeyHandler(v)));
                });
        }
        es.shutdown();
    }
}
