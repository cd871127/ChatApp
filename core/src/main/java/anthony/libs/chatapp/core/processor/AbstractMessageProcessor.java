package anthony.libs.chatapp.core.processor;

import anthony.libs.chatapp.core.message.MessageInfo;

/**
 * Created by chend on 2017/8/16.
 */
public abstract class AbstractMessageProcessor implements MessageProcessor {
//    private SelectionKey selectionKey;
//    protected MessageQueue messageQueue = MessageQueue.getInstance();

    protected abstract void doProcess(MessageInfo messageInfo);


//    protected SelectionKey getSelectionKey() {
//        return selectionKey;
//    }

//    protected void setSelectionKey(SelectionKey selectionKey) {
//        this.selectionKey = selectionKey;
//    }

    @Override
    @SuppressWarnings("unchecked")
    public void process(MessageInfo messageInfo) {
//        V message = (V) messageInfo.getMessage();
//        setSelectionKey(messageInfo.getSelectionKey());
        doProcess(messageInfo);
    }
}
