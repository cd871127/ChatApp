package anthony.libs.chatapp.server.service;

import anthony.libs.chatapp.core.service.impl.MessageResendService;

public class ServerMessageResendService extends MessageResendService {
    private ServerMessageResendService() {
        super(ServerSendMessageService.getInstance());
    }

    private static ServerMessageResendService ourInstance = new ServerMessageResendService();

    public static ServerMessageResendService getInstance() {
        return ourInstance;
    }

}
