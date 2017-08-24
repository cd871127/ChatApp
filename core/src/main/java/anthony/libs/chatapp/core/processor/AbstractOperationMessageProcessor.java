package anthony.libs.chatapp.core.processor;

import anthony.libs.chatapp.core.container.MessagesWaitReplay;
import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.service.impl.SendMessageService;

/**
 * Created by chend on 2017/8/16.
 */
public abstract class AbstractOperationMessageProcessor extends AbstractMessageProcessor {

    private SendMessageService sendMessageService;

    public AbstractOperationMessageProcessor(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    protected void ack(OperationMessage message) {
        ackAck(message);
        sendMessageService.sendAckAck(message);
    }

    protected void ackAck(OperationMessage message) {
        MessagesWaitReplay.getInstance().remove(message.getHeaders().get(OperationMessage.CONFIRM_ID));
    }


}
