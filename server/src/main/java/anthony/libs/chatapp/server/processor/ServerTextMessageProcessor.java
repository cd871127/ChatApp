package anthony.libs.chatapp.server.processor;

import anthony.libs.chatapp.core.container.CachedMessages;
import anthony.libs.chatapp.core.message.TextMessage;
import anthony.libs.chatapp.core.processor.AbstractTextMessageProcessor;
import anthony.libs.chatapp.server.ClientInfo;
import anthony.libs.chatapp.server.container.OnlineClientInfoContainer;

/**
 * Created by chend on 2017/8/14.
 */
public class ServerTextMessageProcessor extends AbstractTextMessageProcessor {
    private OnlineClientInfoContainer clientInfoContainer = OnlineClientInfoContainer.getInstance();

    @Override
    protected void doProcess(TextMessage message) {
        ClientInfo clientInfo = clientInfoContainer.getClientInfoByUserId(message.getDestination());
        if (clientInfo != null) {
            messageQueue.putAndWaitReply(message);
        }else
        {
            CachedMessages.getInstance().put(message.getDestination(),message);
        }
    }
}
