package anthony.libs.chatapp.client.service;

import anthony.libs.chatapp.core.service.impl.MessageResendService;

public class ClientMessageResendService extends MessageResendService {
    private static ClientMessageResendService ourInstance = new ClientMessageResendService();

    private ClientMessageResendService() {
        super(ClientSendMessageService.getInstance());
    }

    public static ClientMessageResendService getInstance() {
        return ourInstance;
    }

}