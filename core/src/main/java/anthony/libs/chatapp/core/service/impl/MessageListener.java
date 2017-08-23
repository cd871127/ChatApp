package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.container.MessageInfoFutureList;
import anthony.libs.chatapp.core.handler.AbstractHandler;
import anthony.libs.chatapp.core.manager.ConnectionManager;
import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.service.AbstractService;

import java.nio.channels.SelectionKey;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chend on 2017/8/10.
 * 监听新的消息
 */
public abstract class MessageListener extends AbstractService {


    private ExecutorService es;
    private ConnectionManager connectionManager;
    private MessageInfoFutureList messageInfoFutureList;

    protected MessageListener() {
        super();
        this.es = Executors.newFixedThreadPool(3);
        this.connectionManager = ConnectionManager.getInstance();
        this.messageInfoFutureList = MessageInfoFutureList.getInstance();
    }


    @Override
    protected void execute() {
        while (getStatus()) {
            Set<SelectionKey> readableKeys = connectionManager.getReadableKeys();
            if (null != readableKeys)
                readableKeys.forEach((v) -> {
                    //开新线程处理消息
                    getLogger().info("new message");
                    messageInfoFutureList.put(es.submit(getHandler(v)));
                });
        }
        es.shutdown();
    }

    protected abstract AbstractHandler<MessageInfo> getHandler(SelectionKey selectionKey);

}
