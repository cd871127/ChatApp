package anthony.libs.chatapp.client.processor;

import anthony.libs.chatapp.core.message.TextMessage;
import anthony.libs.chatapp.core.processor.AbstractTextMessageProcessor;

/**
 * Created by chend on 2017/8/14.
 */
public class ClientTextMessageProcessor extends AbstractTextMessageProcessor {

    @Override
    public void doProcess(TextMessage message) {
        System.out.println("TextMessageProcessor");
//        System.out.println(textMessage);
    }
}
