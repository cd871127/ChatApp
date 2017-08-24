package anthony.libs.chatapp.server.container;


import anthony.libs.chatapp.core.monitor.Monitor;
import anthony.libs.chatapp.server.ClientInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//保存用户信息的容器,内部用两个map
//public class ClientInfoContainer extends AbstractMapBasedContainer<String, ClientInfo> {
public class OnlineClientInfoContainer implements Monitor {
    private static OnlineClientInfoContainer ourInstance = new OnlineClientInfoContainer();
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Map<String, ClientInfo> userIdMap = new HashMap<>();
    private Map<String, ClientInfo> selectionKeyNameMap = new HashMap<>();
    private int onlineUserNum = 0;

    private OnlineClientInfoContainer() {
    }

    public static OnlineClientInfoContainer getInstance() {
        return ourInstance;
    }

    public ClientInfo addClientInfo(ClientInfo clientInfo) {
        String userId = clientInfo.getUserInfo().getUserId();
        String selectionKeyName = clientInfo.getSelectionKey().toString();
        lock.readLock().lock();
        ClientInfo oldClientInfo = userIdMap.containsKey(userId) ? userIdMap.get(userId) : selectionKeyNameMap.get(selectionKeyName);
        lock.readLock().unlock();

        lock.writeLock().lock();
        remove(oldClientInfo);
        userIdMap.put(userId, clientInfo);
        selectionKeyNameMap.put(selectionKeyName, clientInfo);
        onlineUserNum = userIdMap.size();
        lock.writeLock().unlock();
        logger.info("user {} login", userId);
        return oldClientInfo;
    }

    public ClientInfo getClientInfoByUserId(String userId) {
        return getFromMap(userId, userIdMap);
    }

    public ClientInfo getClientInfoBySelectionKey(SelectionKey selectionKey) {
        return getFromMap(selectionKey.toString(), selectionKeyNameMap);
    }

    private ClientInfo getFromMap(String key, Map<String, ClientInfo> map) {
        ClientInfo clientInfo;
        lock.readLock().lock();
        clientInfo = map.get(key);
        lock.readLock().unlock();
        return clientInfo;
    }

    public ClientInfo removeByUserId(String userId) {
        ClientInfo clientInfo = getFromMap(userId, userIdMap);
        if (clientInfo == null)
            return null;
        return remove(clientInfo);
    }

    public ClientInfo removeBySelectionKey(SelectionKey selectionKey) {
        ClientInfo clientInfo = getFromMap(selectionKey.toString(), selectionKeyNameMap);
        if (clientInfo == null)
            return null;
        return remove(clientInfo);
    }

    private ClientInfo remove(ClientInfo clientInfo) {
        if (null == clientInfo)
            return null;
        lock.writeLock().lock();
        userIdMap.remove(clientInfo.getUserInfo().getUserId());
        selectionKeyNameMap.remove(clientInfo.getSelectionKey().toString());
        onlineUserNum = userIdMap.size();
        lock.writeLock().unlock();
        return clientInfo;
    }

    @Override
    public void logInfo() {
        logger.info("{} users online", onlineUserNum);
    }
}
