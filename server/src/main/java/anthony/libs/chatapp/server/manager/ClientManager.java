package anthony.libs.chatapp.server.manager;

import anthony.libs.chatapp.core.container.FutureList;
import anthony.libs.chatapp.core.manager.ConnectionManager;
import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.service.AbstractService;
import anthony.libs.chatapp.server.ClientInfo;
import anthony.libs.chatapp.server.container.OnlineClientInfoContainer;

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
            login(messageInfo);
        }
    }

    private void login(MessageInfo messageInfo) {
        if (messageInfo == null)
            return;
        ClientInfo clientInfo = new ClientInfo(messageInfo.getMessage().getUserInfo());
        SelectionKey selectionKey = connectionManager.registerSocketChannel(messageInfo.getSocketChannel());
        clientInfo.setSelectionKey(selectionKey);
        ClientInfo oldClientInfo = onlineClientInfoContainer.addClientInfo(clientInfo);

        if (null != oldClientInfo) {
            //TODO send another login

        }

        //TODO send Login confirm
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
