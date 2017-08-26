package anthony.libs.chatapp.core.message;

/**
 * Created by chend on 2017/8/14.
 */
public class OperationMessage extends Message<OperationMessage.TYPE> {
    public static String CONFIRM_ID = "CONFIRM_ID";

    public OperationMessage() {
    }

    public OperationMessage(TYPE type) {
        setOperation(type);
    }

    public TYPE getOperation() {
        return getBody();
    }

    public void setOperation(TYPE type) {
        setBody(type);
    }

    @Override
    public byte[] getBodyBytes() {
        return getBody() == null ? null : getBody().value.getBytes(MessageUtil.MESSAGE_CHARSET);
    }

    @Override
    public void setBody(byte[] bodyBytes) {
        String typeStr = new String(bodyBytes, MessageUtil.MESSAGE_CHARSET);
        for (TYPE type : TYPE.values()) {
            if (type.value.equals(typeStr)) {
                setBody(type);
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("**********TextMessage  HEAD************\n");
        getHeaders().forEach((k, v) -> sb.append(k).append(": ").append(v).append("\n"));
        sb.append("**********TextMessage  BODY************\n");
        sb.append(getOperation()).append("\n");
        sb.append("**********TextMessage  END*************\n");
        return sb.toString();
    }

    public enum TYPE {
        LOGIN("LOGIN"), LOGOUT("LOGOUT"), ACK("ACK"), ACK_ACK("ACK_ACK"),
        LOGIN_SUCCESS("LOGIN_SUCCESS"), LOGIN_FAILED("LOGIN_FAILED"), ANOTHER_LOGIN("ANOTHER_LOGIN");

        private String value;

        TYPE(String value) {
            this.value = value;
        }
    }
}
