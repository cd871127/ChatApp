package anthony.libs.chatapp.server.container;

import anthony.libs.chatapp.core.container.AbstractMapBasedContainer;
import anthony.libs.chatapp.core.user.ClientInfo;

public class ClientInfoContainer extends AbstractMapBasedContainer<String, ClientInfo> {
    private static ClientInfoContainer ourInstance = new ClientInfoContainer();

    public static ClientInfoContainer getInstance() {
        return ourInstance;
    }

    private ClientInfoContainer() {
        super();
    }

    //如果有相同的clientinfo就返回前一个,然后用后面的替换掉,
    public ClientInfo addClientInfo(ClientInfo clientInfo) {
        ClientInfo oldClientInfo = get(clientInfo.getUserInfo().getUserId());
        put(clientInfo.getUserInfo().getUserId(), clientInfo);
        return oldClientInfo;
    }
}
