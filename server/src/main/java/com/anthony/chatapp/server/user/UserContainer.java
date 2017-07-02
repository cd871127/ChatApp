package com.anthony.chatapp.server.user;

import com.anthony.chatapp.core.user.UserInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by chend on 2017/6/28.
 * 保存已登陆的用户信息
 */
public class UserContainer {

    private Map<String, UserInfo> container;
    private static UserContainer userContainer;
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private UserContainer() {
        container = new HashMap<>();
    }

    static {
        userContainer = new UserContainer();
    }

    public static UserContainer getInstance() {
        return userContainer;
    }

    public boolean addUser(UserInfo userInfo) {
        boolean isLock;
        try {
            isLock = rwl.writeLock().tryLock(3, SECONDS);
        } catch (InterruptedException e) {
            isLock = false;
            e.printStackTrace();
        }
        if (!isLock)
            return false;

        /*add user*/
        container.put(userInfo.getUserName(), userInfo);

        rwl.writeLock().unlock();
        return true;
    }

    public UserInfo getUserByKey(String key) {
        boolean isLock;
        try {
            isLock = rwl.readLock().tryLock(3, SECONDS);
        } catch (InterruptedException e) {
            isLock = false;
            e.printStackTrace();
        }

        if (!isLock)
            return null;

        UserInfo userInfo = container.get(key);
        rwl.readLock().unlock();

        return userInfo;
    }

    public boolean removeUserByKey(String key) {
        boolean isLock;
        try {
            isLock = rwl.writeLock().tryLock(3, SECONDS);
        } catch (InterruptedException e) {
            isLock = false;
            e.printStackTrace();
        }
        if (!isLock)
            return false;

        container.remove(key);

        rwl.writeLock().unlock();
        return true;
    }


}
