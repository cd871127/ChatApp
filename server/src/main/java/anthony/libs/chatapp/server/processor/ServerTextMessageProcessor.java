package anthony.libs.chatapp.server.processor;

import anthony.libs.chatapp.core.message.MessageUtil;
import anthony.libs.chatapp.core.message.TextMessage;
import anthony.libs.chatapp.core.processor.AbstractTextMessageProcessor;
import anthony.libs.chatapp.core.user.ClientInfo;
import anthony.libs.chatapp.server.container.ClientInfoContainer;

import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/14.
 */
public class ServerTextMessageProcessor extends AbstractTextMessageProcessor {
    private ClientInfoContainer clientInfoContainer = ClientInfoContainer.getInstance();

    @Override
    protected void doProcess(TextMessage message) {
        ClientInfo clientInfo = clientInfoContainer.get(message.getDestination());
        if (clientInfo != null) {
            MessageUtil.sendMessage(message, (SocketChannel) clientInfo.getSelectionKey().channel());
        }
    }
}
