package anthony.libs.chatapp.client.processor.factory;

import anthony.libs.chatapp.client.processor.ClientOperationMessageProcessor;
import anthony.libs.chatapp.client.processor.ClientTextMessageProcessor;
import anthony.libs.chatapp.core.processor.MessageProcessor;
import anthony.libs.chatapp.core.processor.factory.AbstractMessageProcessorFactory;

/**
 * Created by chend on 2017/8/16.
 */
public class ClientMessageProcessorFactory extends AbstractMessageProcessorFactory {
    @Override
    public MessageProcessor getProcessor(String messageClassName) {
        MessageProcessor messageProcessor;
        switch (messageClassName) {
            case "anthony.libs.chatapp.core.message.TextMessage":
                messageProcessor = new ClientTextMessageProcessor();
                break;
            case "anthony.libs.chatapp.core.message.OperationMessage":
                messageProcessor = new ClientOperationMessageProcessor();
                break;
            default:
                messageProcessor = null;
                break;
        }
        return messageProcessor;
    }
}
