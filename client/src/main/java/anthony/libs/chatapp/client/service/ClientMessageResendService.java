package anthony.libs.chatapp.client.service;

import anthony.libs.chatapp.core.service.impl.MessageResendService;

public class ClientMessageResendService extends MessageResendService {
    private ClientMessageResendService() {
        super(ClientSendMessageService.getInstance());
    }

    private static ClientMessageResendService ourInstance = new ClientMessageResendService();

    public static ClientMessageResendService getInstance() {
        return ourInstance;
    }

}