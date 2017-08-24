package anthony.libs.chatapp.client.handler;

import anthony.libs.chatapp.core.handler.MessageHandler;

import java.nio.channels.SelectionKey;


public class ClientMessageHandler extends MessageHandler {
    public ClientMessageHandler(SelectionKey selectionKey) {
        super(selectionKey);
    }

    @Override
    protected void exceptionHandle(Exception e) {
        e.printStackTrace();
    }
}