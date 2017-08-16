package anthony.libs.chatapp.server.processor;

import anthony.libs.chatapp.core.message.TextMessage;
import anthony.libs.chatapp.core.processor.AbstractTextMessageProcessor;

/**
 * Created by chend on 2017/8/14.
 */
public class ServerTextMessageProcessor extends AbstractTextMessageProcessor {

    @Override
    protected void doProcess(TextMessage message) {
        System.out.println("TextMessageProcessor");
    }
}
