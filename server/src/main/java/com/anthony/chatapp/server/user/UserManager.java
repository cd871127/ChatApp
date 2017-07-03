package com.anthony.chatapp.server.user;


import com.anthony.chatapp.core.user.UserInfo;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by chend on 2017/6/28.
 * 保存已登陆的用户信息
 */
public class UserManager {

    private Map<String, UserInfo> container;
    private static UserManager userManager;
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private UserManager() {
        container = new HashMap<>();
    }

    static {
        userManager = new UserManager();
    }

    public static UserManager getInstance() {
        return userManager;
    }

    public boolean userLogin(UserInfo userInfo) {
        rwl.writeLock().lock();
        /*add user*/
        container.put(userInfo.getUserName(), userInfo);
        rwl.writeLock().unlock();
        return true;
    }

    public UserInfo getUserInfo(String key) {
        rwl.readLock().lock();
        UserInfo userInfo = container.get(key);
        rwl.readLock().unlock();
        return userInfo;
    }

    public boolean userLogout(String key) {
        rwl.writeLock().lock();
        container.remove(key);
        rwl.writeLock().unlock();
        return true;
    }

    public SocketChannel getUserChannel(String key) {
        return getUserInfo(key).getSocketChannel();
    }

}
