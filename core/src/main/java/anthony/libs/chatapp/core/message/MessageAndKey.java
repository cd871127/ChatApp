package anthony.libs.chatapp.core.message;

import java.nio.channels.SelectionKey;

/**
 * Created by chend on 2017/8/16.
 */
public class MessageAndKey {
    private Message message;
    private SelectionKey selectionKey;//message从哪个key读出来的

    public MessageAndKey() {
    }

    public MessageAndKey(Message message, SelectionKey selectionKey) {
        this.message = message;
        this.selectionKey = selectionKey;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public SelectionKey getSelectionKey() {
        return selectionKey;
    }

    public void setSelectionKey(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }
}
