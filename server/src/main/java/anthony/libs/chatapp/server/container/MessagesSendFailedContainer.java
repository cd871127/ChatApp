package anthony.libs.chatapp.server.container;

import anthony.libs.chatapp.core.container.AbstractMapBasedContainer;
import anthony.libs.chatapp.core.message.Message;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by chend on 2017/8/21.
 * 缓存没有找到收件人的消息
 */
public class MessagesSendFailedContainer extends AbstractMapBasedContainer<String, ArrayList<Message>> {
    private static MessagesSendFailedContainer ourInstance = new MessagesSendFailedContainer();

    private MessagesSendFailedContainer() {

    }

    public static MessagesSendFailedContainer getInstance() {
        return ourInstance;
    }


    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String userId, Message message) {
        lock.writeLock().lock();
        ArrayList<Message> userMessage;
        if (containsKey(userId))
            userMessage = get(userId);
        else
            userMessage = new ArrayList<>();
        userMessage.add(message);
        put(userId, userMessage);
        lock.writeLock().unlock();
    }


    @Override
    public ArrayList<Message> get(String userId) {
        lock.readLock().lock();
        ArrayList<Message> userMessage = super.get(userId);
        lock.readLock().unlock();
        return userMessage;
    }

    public ArrayList<Message> getMessagesByUserId(String userId) {
        lock.writeLock().lock();
        ArrayList<Message> userMessage = this.get(userId);
        remove(userId);
        lock.writeLock().unlock();
        return userMessage;
    }

}
