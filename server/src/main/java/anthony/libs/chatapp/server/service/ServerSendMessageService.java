package anthony.libs.chatapp.server.service;

import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.service.impl.SendMessageService;
import anthony.libs.chatapp.server.ClientInfo;
import anthony.libs.chatapp.server.container.MessagesSendFailedContainer;
import anthony.libs.chatapp.server.container.OnlineClientInfoContainer;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;

public class ServerSendMessageService extends SendMessageService {
    private MessagesSendFailedContainer messagesSendFailedContainer = MessagesSendFailedContainer.getInstance();
    private static ServerSendMessageService ourInstance = new ServerSendMessageService();

    private OnlineClientInfoContainer clientInfoContainer = OnlineClientInfoContainer.getInstance();

    public static ServerSendMessageService getInstance() {
        return ourInstance;
    }

    private ServerSendMessageService() {
        super(3,ServerMessageResendService.getInstance());
    }

    @Override
    protected void dealNullKey(Message message) {
        messagesSendFailedContainer.put(message.getDestination(), message);
    }

    @Override
    protected SelectionKey getTargetKey(String destination) {
        ClientInfo clientInfo = clientInfoContainer.getClientInfoByUserId(destination);
        return clientInfo == null ? null : clientInfo.getSelectionKey();
    }

    public void sendOfflineMessages(String userId) {
        ArrayList<Message> offlineMessage = messagesSendFailedContainer.getMessagesByUserId(userId);
        if (null != offlineMessage)
            offlineMessage.forEach((this::sendMessage));
    }


}
