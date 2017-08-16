package anthony.libs.chatapp.core.message;

import anthony.libs.chatapp.core.user.UserInfo;

import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Anthony on 2017/8/1.UUID.randomUUID().toString().replace("-", "");
 */
public abstract class Message<T> {
    private Map<String, String> headers = new HashMap<>();
    private T body;

    public Message() {
        headers.put("id", UUID.randomUUID().toString().replace("-", ""));
        headers.put("class-name", getClass().getName());
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public abstract byte[] getBodyBytes();

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public abstract void setBody(byte[] bodyBytes);

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setSender(String sender) {
        headers.put(HEAD_SENDER, sender);
    }

    public String getSender() {
        return headers.get(HEAD_SENDER);
    }

    public void setDestination(String destination) {
        headers.put(HEAD_DESTINATION, destination);
    }

    public String getDestination() {
        return headers.get(HEAD_DESTINATION);
    }

    public void setUserInfo(UserInfo userInfo) {
        headers.put(HEAD_USER_INFO, userInfo.toString());
    }

    public UserInfo getUserInfo() {
        return UserInfo.buildUserInfoFromJSONString(headers.get(HEAD_USER_INFO));
    }

    public void setOneHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getOneHeader(String key) {
        return headers.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Message))
            return false;
        return o == this || this.headers.get("id").equals(((Message) o).headers.get("id")) && this.body.equals(((Message) o).body);
    }

    @Override
    public int hashCode() {
        return headers.get("id").hashCode();
    }

    public String HEAD_USER_INFO = "USER_INFO";
    public String HEAD_SENDER = "SENDER";
    public String HEAD_DESTINATION = "DESTINATION";
}
