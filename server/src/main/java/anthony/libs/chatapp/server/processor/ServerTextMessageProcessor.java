package anthony.libs.chatapp.server.processor;

import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.message.TextMessage;
import anthony.libs.chatapp.core.processor.AbstractTextMessageProcessor;
import anthony.libs.chatapp.server.service.ServerSendMessageService;

/**
 * Created by chend on 2017/8/14.
 */
public class ServerTextMessageProcessor extends AbstractTextMessageProcessor {
    private ServerSendMessageService serverSendMessageService = ServerSendMessageService.getInstance();

    @Override
    protected void doProcess(MessageInfo messageInfo) {
        TextMessage message = (TextMessage) messageInfo.getMessage();
        serverSendMessageService.sendMessageForReplay(message);
        serverSendMessageService.sendAck(message);
    }
}
