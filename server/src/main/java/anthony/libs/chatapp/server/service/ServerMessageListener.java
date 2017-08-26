package anthony.libs.chatapp.server.service;

import anthony.libs.chatapp.core.handler.AbstractHandler;
import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.service.impl.MessageListener;
import anthony.libs.chatapp.server.handler.ServerMessageHandler;

import java.nio.channels.SelectionKey;

/**
 * Created by chend on 2017/8/22.
 */
public class ServerMessageListener extends MessageListener {

    private static ServerMessageListener ourInstance = new ServerMessageListener();

    private ServerMessageListener() {

    }

    public static ServerMessageListener getInstance() {
        return ourInstance;
    }

    @Override
    protected AbstractHandler<MessageInfo> getHandler(SelectionKey selectionKey) {
        return new ServerMessageHandler(selectionKey);
    }
}
