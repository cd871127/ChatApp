package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.message.Message;

/**
 * Created by chend on 2017/8/16.
 */
public class MessageQueue extends AbstractBlockingQueueBasedContainer<Message> {
    private static MessageQueue ourInstance = new MessageQueue();

    public static MessageQueue getInstance() {
        return ourInstance;
    }

    private MessageQueue() {

    }

    @Override
    public Message take() {
        Message message;
        try {
            message = super.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            message = null;
        }
        return message;
    }

    @Override
    public void put(Message message) {
        try {
            super.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void putAndWaitReply(Message message) {
        CachedMessageContainer.getInstance().put(message.getId(), message);
        put(message);
    }

}
