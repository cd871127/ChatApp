package anthony.libs.chatapp.core.message;

import java.nio.channels.SocketChannel;

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
    private SocketChannel socketChannel;//接收消息的channel
    private long sendTime; //发送消息的时间

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

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

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
