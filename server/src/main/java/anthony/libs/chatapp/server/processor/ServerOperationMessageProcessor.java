package anthony.libs.chatapp.server.processor;

import anthony.libs.chatapp.core.message.MessageUtil;
import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.processor.AbstractOperationMessageProcessor;
import anthony.libs.chatapp.core.user.ClientInfo;
import anthony.libs.chatapp.server.container.ClientInfoContainer;

import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/14.
 */
public class ServerOperationMessageProcessor extends AbstractOperationMessageProcessor {

    private ClientInfoContainer clientInfoContainer = ClientInfoContainer.getInstance();

    @Override
    protected void doProcess(OperationMessage message) {
        System.out.println("OperationMessageProcessor");
        switch (message.getOperation()) {
            case LOGIN:
                login(message);
                break;
        }
    }

    private void login(OperationMessage message) {
        ClientInfo newClientInfo = new ClientInfo();
        newClientInfo.setSelectionKey(getSelectionKey());
        newClientInfo.setUserInfo(message.getUserInfo());
        ClientInfo oldClientInfo = clientInfoContainer.addClientInfo(newClientInfo);
        if (null != oldClientInfo)
            MessageUtil.sendMessage(new OperationMessage(OperationMessage.TYPE.ANOTHER_LOGIN),
                    (SocketChannel) oldClientInfo.getSelectionKey().channel());
        MessageUtil.sendMessage(new OperationMessage(OperationMessage.TYPE.LOGIN_SUCCESS),
                (SocketChannel) getSelectionKey().channel());
    }

}
