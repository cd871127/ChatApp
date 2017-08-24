package anthony.libs.chatapp.server.manager;

import anthony.libs.chatapp.core.container.FutureList;
import anthony.libs.chatapp.core.manager.ConnectionManager;
import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.service.AbstractService;
import anthony.libs.chatapp.server.ClientInfo;
import anthony.libs.chatapp.server.container.OnlineClientInfoContainer;
import anthony.libs.chatapp.server.service.ServerSendMessageService;

import java.nio.channels.SelectionKey;
import java.util.concurrent.Future;

/**
 * Created by chend on 2017/8/22.
 * 客户端管理
 */
public class ClientManager extends AbstractService {
    private static ClientManager ourInstance = new ClientManager();

    public static ClientManager getInstance() {
        return ourInstance;
    }

    private ClientManager() {
    }

    private OnlineClientInfoContainer onlineClientInfoContainer = OnlineClientInfoContainer.getInstance();
    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    /**
     * 服务:处理用户登录信息
     */
    @Override
    protected void execute() {
        while (getStatus()) {
            MessageInfo messageInfo = getLoginInfo();
            if (userAuthentication(messageInfo))
                login(messageInfo);
            else {
                OperationMessage loginFailed = new OperationMessage(OperationMessage.TYPE.LOGIN_FAILED);
                ServerSendMessageService.sendMessageViaSocketChannel(loginFailed, messageInfo.getSocketChannel());
            }
        }
    }

    //验证用户
    private boolean userAuthentication(MessageInfo messageInfo) {
        return true;
    }


    //登陆
    private void login(MessageInfo messageInfo) {
        if (messageInfo == null)
            return;
        ClientInfo clientInfo = new ClientInfo(messageInfo.getMessage().getUserInfo());

        ClientInfo oldClientInfo = onlineClientInfoContainer.getClientInfoByUserId(clientInfo.getUserInfo().getUserId());

        if (null != oldClientInfo) {
            //send another login
            OperationMessage anotherLogin = new OperationMessage(OperationMessage.TYPE.ANOTHER_LOGIN);
            anotherLogin.setDestination(oldClientInfo.getUserInfo().getUserId());
            ServerSendMessageService.getInstance().sendMessage(anotherLogin);
        }

        SelectionKey selectionKey = connectionManager.registerSocketChannel(messageInfo.getSocketChannel());
        clientInfo.setSelectionKey(selectionKey);
        onlineClientInfoContainer.addClientInfo(clientInfo);

        //send Login confirm
        OperationMessage confirm = new OperationMessage(OperationMessage.TYPE.LOGIN_SUCCESS);
        confirm.setDestination(clientInfo.getUserInfo().getUserId());
        ServerSendMessageService.getInstance().sendMessage(confirm);

        //发送离线时收到的消息
        ServerSendMessageService.getInstance().sendOfflineMessages(messageInfo.getMessage().getUserInfo().getUserId());

    }

    private FutureList<MessageInfo> loginInfoList = new FutureList<>();


    public void clientLogin(Future<MessageInfo> loginInfo) {
        loginInfoList.put(loginInfo);
    }

    public void clientLogout(SelectionKey selectionKey) {
        ClientInfo clientInfo = onlineClientInfoContainer.removeBySelectionKey(selectionKey);
        if (selectionKey.isValid())
            selectionKey.cancel();
        getLogger().info("user {} logout", clientInfo.getUserInfo().getUserId());
    }

    /**
     * @return 从futureList中获取一个用户登陆的信息
     */
    private MessageInfo getLoginInfo() {
        return loginInfoList.getElement();
    }
}
