package anthony.libs.chatapp.client.service;

import anthony.libs.chatapp.client.Client;
import anthony.libs.chatapp.core.service.impl.SendMessageService;

import java.nio.channels.SelectionKey;

public class ClientSendMessageService extends SendMessageService {
    private static ClientSendMessageService ourInstance = new ClientSendMessageService();

    public static ClientSendMessageService getInstance() {
        return ourInstance;
    }

    private ClientSendMessageService() {
        super(2);
    }

    @Override
    protected SelectionKey getTargetKey(String destination) {
        return Client.getSelectionKey();
    }
}
