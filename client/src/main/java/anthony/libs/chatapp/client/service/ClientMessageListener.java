package anthony.libs.chatapp.client.service;

import anthony.libs.chatapp.client.handler.ClientMessageHandler;
import anthony.libs.chatapp.core.handler.AbstractHandler;
import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.service.impl.MessageListener;

import java.nio.channels.SelectionKey;

public class ClientMessageListener extends MessageListener {
    private static ClientMessageListener ourInstance = new ClientMessageListener();

    public static ClientMessageListener getInstance() {
        return ourInstance;
    }

    private ClientMessageListener() {

    }

    @Override
    protected AbstractHandler<MessageInfo> getHandler(SelectionKey selectionKey) {
        return new ClientMessageHandler(selectionKey);
    }
}
