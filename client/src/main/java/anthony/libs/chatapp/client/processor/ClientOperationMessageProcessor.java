package anthony.libs.chatapp.client.processor;

import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.processor.AbstractOperationMessageProcessor;

/**
 * Created by chend on 2017/8/14.
 */
public class ClientOperationMessageProcessor extends AbstractOperationMessageProcessor {
    @Override
    public void doProcess(MessageInfo messageInfo ) {
        OperationMessage message= (OperationMessage) messageInfo.getMessage();
        System.out.println("OperationMessageProcessor");
        switch (message.getOperation()) {
            case LOGIN_SUCCESS:
                break;
            case ANOTHER_LOGIN:
                break;
            case ACK:
                break;
            case ACK_ACK:
                break;
        }
    }

    private void ack(OperationMessage message)
    {}

    private void ackAck(OperationMessage message)
    {}
}
