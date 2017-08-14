package anthony.libs.chatapp.core.processor.factory;

import anthony.libs.chatapp.core.processor.MessageProcessor;
import anthony.libs.chatapp.core.processor.TextMessageProcessor;

/**
 * Created by chend on 2017/8/14.
 */
public class MessageProcessorFactory {
    public static MessageProcessor getProcessor(String messageClassName) {
        MessageProcessor messageProcessor;
        switch (messageClassName) {
            case "anthony.libs.chatapp.core.message.TextMessage":
                messageProcessor = new TextMessageProcessor();
                break;
            default:
                messageProcessor = null;
                break;
        }
        return messageProcessor;
    }
}
