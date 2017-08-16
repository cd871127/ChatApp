package anthony.libs.chatapp.server.processor.factory;

import anthony.libs.chatapp.core.processor.MessageProcessor;
import anthony.libs.chatapp.core.processor.factory.AbstractMessageProcessorFactory;
import anthony.libs.chatapp.server.processor.ServerOperationMessageProcessor;
import anthony.libs.chatapp.server.processor.ServerTextMessageProcessor;

/**
 * Created by chend on 2017/8/16.
 */
public class ServerMessageProcessorFactory extends AbstractMessageProcessorFactory {
    @Override
    public MessageProcessor getProcessor(String messageClassName) {
        MessageProcessor messageProcessor;
        switch (messageClassName) {
            case "anthony.libs.chatapp.core.message.TextMessage":
                messageProcessor = new ServerTextMessageProcessor();
                break;
            case "anthony.libs.chatapp.core.message.OperationMessage":
                messageProcessor = new ServerOperationMessageProcessor();
                break;
            default:
                messageProcessor = null;
                break;
        }
        return messageProcessor;
    }
}
