package com.anthony.chatapp.server.user;


import com.anthony.chatapp.core.user.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by chend on 2017/6/28.
 * 保存已登陆的用户信息
 */
public class UserManager {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, UserAndKey> container;
    private Map<SelectionKey, String> keyUser;
    private static UserManager userManager;
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private UserManager() {
        container = new HashMap<>();
        keyUser = new HashMap<>();
    }

    static {
        userManager = new UserManager();
    }

    public static UserManager getInstance() {
        return userManager;
    }

    public boolean userLogin(UserAndKey userAndKey) {
        rwl.writeLock().lock();
        /*add user*/
        container.put(userAndKey.getUserInfo().getUserId(), userAndKey);
        keyUser.put(userAndKey.getKey(), userAndKey.getUserInfo().getUserId());
        logger.debug("login: " + userAndKey.getUserInfo().getUserId());
        rwl.writeLock().unlock();
        return true;
    }

    public UserInfo getUserInfo(String key) {
        rwl.readLock().lock();
        UserAndKey userAndKey = container.get(key);
        rwl.readLock().unlock();
        return userAndKey == null ? null : userAndKey.getUserInfo();
    }

    public boolean userLogout(String key) {
        rwl.writeLock().lock();
        keyUser.remove(getUserSelectionKey(key));
        container.remove(key);
        logger.debug("logout: " + key);
        rwl.writeLock().unlock();
        return true;
    }

    public SelectionKey getUserSelectionKey(String key) {
        rwl.readLock().lock();
        UserAndKey userAndKey = container.get(key);
        rwl.readLock().unlock();
        return userAndKey == null ? null : userAndKey.getKey();
    }

    public void removeUserByKey(SelectionKey key) {
        rwl.writeLock().lock();
        String userId = keyUser.get(key);
        container.remove(userId);
        keyUser.remove(key);
        logger.debug("remove user: " + userId);
        rwl.writeLock().unlock();
    }

}
