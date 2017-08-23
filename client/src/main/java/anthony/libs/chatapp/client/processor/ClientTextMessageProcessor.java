package anthony.libs.chatapp.client.processor;

import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.message.TextMessage;
import anthony.libs.chatapp.core.processor.AbstractTextMessageProcessor;

/**
 * Created by chend on 2017/8/14.
 */
public class ClientTextMessageProcessor extends AbstractTextMessageProcessor {

    @Override
    public void doProcess(MessageInfo messageInfo ) {
        TextMessage message = (TextMessage) messageInfo.getMessage();
        System.out.println(message.toString());
    }
}
