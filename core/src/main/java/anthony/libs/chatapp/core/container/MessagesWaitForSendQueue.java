package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.monitor.Monitor;

/**
 * Created by chend on 2017/8/16.
 * 待发送的message列表
 */
public class MessagesWaitForSendQueue extends AbstractBlockingQueueBasedContainer<Message> implements Monitor {
    private static MessagesWaitForSendQueue ourInstance = new MessagesWaitForSendQueue();

    private MessagesWaitForSendQueue() {

    }

    public static MessagesWaitForSendQueue getInstance() {
        return ourInstance;
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

    @Override
    public void logInfo() {
        getLogger().info("{} messages wait for send", size());
    }
}
