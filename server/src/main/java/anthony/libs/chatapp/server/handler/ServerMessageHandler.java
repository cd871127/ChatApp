package anthony.libs.chatapp.server.handler;

import anthony.libs.chatapp.core.handler.MessageHandler;
import anthony.libs.chatapp.server.manager.ClientManager;

import java.nio.channels.SelectionKey;

/**
 * Created by chend on 2017/8/22.
 */
public class ServerMessageHandler extends MessageHandler {
    public ServerMessageHandler(SelectionKey selectionKey) {
        super(selectionKey);
    }

    private ClientManager clientManager = ClientManager.getInstance();

    @Override
    protected void exceptionHandle(Exception e) {
        e.printStackTrace();
        clientManager.clientLogout(getSelectionKey());
    }
}
