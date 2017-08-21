package anthony.libs.chatapp.core.message;

/**
 * Created by chend on 2017/8/21.
 */
public class MessageInfo {
    public MessageInfo() {

    }

    public MessageInfo(Message message) {
        this();
        setMessage(message);
    }

    public MessageInfo(Message message, long lastActiveTime) {
        this(message);
        setLastActiveTime(lastActiveTime);
    }

    private Message message;
    private long lastActiveTime;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public long getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(long lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }
}
