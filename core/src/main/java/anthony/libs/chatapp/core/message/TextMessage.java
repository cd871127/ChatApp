package anthony.libs.chatapp.core.message;

/**
 * Created by Anthony on 2017/8/1.
 * 文本消息类
 */
public class TextMessage extends Message<String> {

    public TextMessage() {
        super();
    }

    public TextMessage(String text) {
        this();
        setText(text);
    }

    public String getText() {
        return getBody();
    }

    public void setText(String text) {
        setBody(text);
    }

    @Override
    public byte[] getBodyBytes() {
        return getBody() == null ? null : getBody().getBytes(MessageUtil.MESSAGE_CHARSET);
    }

    @Override
    public void setBody(byte[] bodyBytes) {
        setBody(new String(bodyBytes, MessageUtil.MESSAGE_CHARSET));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("**********TextMessage  HEAD************\n");
        getHeaders().forEach((k, v) -> sb.append(k).append(": ").append(v).append("\n"));
        sb.append("**********TextMessage  BODY************\n");
        sb.append(getText()).append("\n");
        sb.append("**********TextMessage  END*************\n");
        return sb.toString();
    }
}
