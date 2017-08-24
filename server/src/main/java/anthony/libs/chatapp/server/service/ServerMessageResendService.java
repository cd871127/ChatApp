package anthony.libs.chatapp.server.service;

import anthony.libs.chatapp.core.service.impl.MessageResendService;

public class ServerMessageResendService extends MessageResendService {
    private static ServerMessageResendService ourInstance = new ServerMessageResendService();

    private ServerMessageResendService() {
        super(ServerSendMessageService.getInstance());
    }

    public static ServerMessageResendService getInstance() {
        return ourInstance;
    }

}
