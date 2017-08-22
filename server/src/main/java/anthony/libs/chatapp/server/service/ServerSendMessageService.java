package anthony.libs.chatapp.server.service;

import anthony.libs.chatapp.core.service.impl.SendMessageService;
import anthony.libs.chatapp.server.container.OnlineClientInfoContainer;

import java.nio.channels.SelectionKey;

public class ServerSendMessageService extends SendMessageService {
    private static ServerSendMessageService ourInstance = new ServerSendMessageService();

    private OnlineClientInfoContainer clientInfoContainer = OnlineClientInfoContainer.getInstance();

    public static ServerSendMessageService getInstance() {
        return ourInstance;
    }

    private ServerSendMessageService() {
        super(3);
    }

    @Override
    protected SelectionKey getTargetKey(String destination) {
        return clientInfoContainer.getClientInfoByUserId(destination).getSelectionKey();
    }
}
