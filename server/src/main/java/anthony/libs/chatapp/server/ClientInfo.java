package anthony.libs.chatapp.server;

import anthony.libs.chatapp.core.user.UserInfo;

import java.nio.channels.SelectionKey;

/**
 * Created by chend on 2017/8/15.
 */
public class ClientInfo {
    public ClientInfo() {
        loginTime = System.currentTimeMillis();
    }

    public ClientInfo(UserInfo userInfo) {
        this();
        this.userInfo = userInfo;
    }

    private UserInfo userInfo;
    private long loginTime;
    private SelectionKey selectionKey;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public SelectionKey getSelectionKey() {
        return selectionKey;
    }

    public void setSelectionKey(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }
}
