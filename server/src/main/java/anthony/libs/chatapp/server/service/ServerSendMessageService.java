package anthony.libs.chatapp.server.service;

import anthony.libs.chatapp.core.service.impl.SendMessageService;
import anthony.libs.chatapp.server.container.ClientInfoContainer;

import java.nio.channels.SelectionKey;

public class ServerSendMessageService extends SendMessageService {
    private static ServerSendMessageService ourInstance = new ServerSendMessageService();

    private ClientInfoContainer clientInfoContainer = ClientInfoContainer.getInstance();

    public static ServerSendMessageService getInstance() {
        return ourInstance;
    }

    private ServerSendMessageService() {
        super(3);
    }

    @Override
    protected SelectionKey getTargetKey(String destination) {
        return clientInfoContainer.get(destination).getSelectionKey();
    }
}
