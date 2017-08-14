package anthony.libs.chatapp.core.message;

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
    private SelectionKey selectionKey;

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
        headers.put("SENDER", sender);
    }

    public String getSender() {
        return headers.get("SENDER");
    }

    public void setDestination(String destination) {
        headers.put("DESTINATION", destination);
    }

    public String getDestination() {
        return headers.get("DESTINATION");
    }

    public void setSelectionKey(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    public SelectionKey getSelectionKey() {
        return selectionKey;
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
}
