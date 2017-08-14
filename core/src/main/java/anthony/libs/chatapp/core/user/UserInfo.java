package anthony.libs.chatapp.core.user;

import com.alibaba.fastjson.JSON;

import java.nio.channels.SelectionKey;

public class UserInfo {
    private String userId;
    private String userName;
    private String passWord;

    public UserInfo() {
    }

    public UserInfo buildUserInfoFromJSONString(String JSONString) {
        return JSON.parseObject(JSONString, getClass());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        System.out.println(JSON.toJSONString(this));
        return JSON.toJSONString(this);
    }
}
