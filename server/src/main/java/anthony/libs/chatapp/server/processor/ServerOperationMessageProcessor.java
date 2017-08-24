package anthony.libs.chatapp.server.processor;

import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.processor.AbstractOperationMessageProcessor;
import anthony.libs.chatapp.core.service.impl.SendMessageService;
import anthony.libs.chatapp.server.service.ServerSendMessageService;

/**
 * Created by chend on 2017/8/14.
 */
public class ServerOperationMessageProcessor extends AbstractOperationMessageProcessor {

    public ServerOperationMessageProcessor() {
        this(ServerSendMessageService.getInstance());
    }

    public ServerOperationMessageProcessor(SendMessageService sendMessageService) {
        super(sendMessageService);
    }

    @Override
    protected void doProcess(MessageInfo messageInfo) {
        System.out.println("OperationMessageProcessor");
        OperationMessage message = (OperationMessage) messageInfo.getMessage();
        switch (message.getOperation()) {
            case LOGIN:
                login(message);
                break;
            case ACK:
                ack(message);
                break;
            case ACK_ACK:
                ackAck(message);
                break;
        }
    }

    private void login(OperationMessage message) {
//        ClientInfo newClientInfo = new ClientInfo();
//        newClientInfo.setUserInfo(message.getUserInfo());
//        ClientInfo oldClientInfo = onlineClientInfoContainer.addClientInfo(newClientInfo);
//        //顶掉以前的登陆信息
//        if (null != oldClientInfo) {
//            OperationMessage antherLogin = new OperationMessage(OperationMessage.TYPE.ANOTHER_LOGIN);
//            antherLogin.setDestination(oldClientInfo.getUserInfo().getUserId());
//            messageQueue.put(antherLogin);
//        }
//        //发送登陆成功确认
//        OperationMessage loginSuccess = new OperationMessage(OperationMessage.TYPE.LOGIN_SUCCESS);
//        loginSuccess.setDestination(newClientInfo.getUserInfo().getUserId());
//        messageQueue.put(loginSuccess);
//        cachedMessages.sendCachedMessage(newClientInfo.getUserInfo().getUserId());

    }


}
