package anthony.libs.chatapp.client;

import anthony.libs.chatapp.core.user.UserInfo;

/**
 * Created by chend on 2017/8/15.
 */
public class ClientInfo {
    public ClientInfo() {
    }

    public ClientInfo(UserInfo userInfo) {
        this();
        this.userInfo = userInfo;
    }

    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }


}
