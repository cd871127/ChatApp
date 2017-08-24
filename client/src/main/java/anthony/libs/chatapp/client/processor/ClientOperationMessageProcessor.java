package anthony.libs.chatapp.client.processor;

import anthony.libs.chatapp.client.service.ClientSendMessageService;
import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.processor.AbstractOperationMessageProcessor;
import anthony.libs.chatapp.core.service.impl.SendMessageService;

/**
 * Created by chend on 2017/8/14.
 */
public class ClientOperationMessageProcessor extends AbstractOperationMessageProcessor {
    public ClientOperationMessageProcessor() {
        this(ClientSendMessageService.getInstance());
    }

    public ClientOperationMessageProcessor(SendMessageService sendMessageService) {
        super(sendMessageService);
    }

    @Override
    public void doProcess(MessageInfo messageInfo) {
        OperationMessage message = (OperationMessage) messageInfo.getMessage();
        System.out.println("OperationMessageProcessor");
        switch (message.getOperation()) {
            case LOGIN_SUCCESS:
                break;
            case ANOTHER_LOGIN:
                break;
            case ACK:
                ack(message);
                break;
            case ACK_ACK:
                ackAck(message);
                break;
        }
    }


}
