package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.message.MessageInfo;

/**
 * Created by chend on 2017/8/16.
 * 待发送的message列表
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
//        MessagesWaitReplay.getInstance().put(message.getId(), new MessageInfo(message, System.currentTimeMillis()));
        put(message);
    }

}
