package anthony.libs.chatapp.core.processor;

import anthony.libs.chatapp.core.container.MessageQueue;
import anthony.libs.chatapp.core.message.MessageAndKey;

import java.nio.channels.SelectionKey;

/**
 * Created by chend on 2017/8/16.
 */
public abstract class AbstractMessageProcessor<V> implements MessageProcessor {
    private V message;
    private SelectionKey selectionKey;
    protected MessageQueue messageQueue = MessageQueue.getInstance();

    protected abstract void doProcess(V message);

    protected V getMessage() {
        return message;
    }

    protected void setMessage(V message) {
        this.message = message;
    }

    protected SelectionKey getSelectionKey() {
        return selectionKey;
    }

    protected void setSelectionKey(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void process(MessageAndKey messageAndKey) {
        V message = (V) messageAndKey.getMessage();
        setSelectionKey(messageAndKey.getSelectionKey());
        doProcess(message);
    }
}
