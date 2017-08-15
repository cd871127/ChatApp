package anthony.libs.chatapp.core.user;

import com.alibaba.fastjson.JSON;

public class UserInfo {
    private String userId;
    private String userName;
    private String passWord;

    public UserInfo() {
    }

    public UserInfo(String userName, String passWord) {
        this();
        this.userName = userName;
        this.passWord = passWord;
    }

    public static UserInfo buildUserInfoFromJSONString(String JSONString) {
        return JSON.parseObject(JSONString, UserInfo.class);
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
