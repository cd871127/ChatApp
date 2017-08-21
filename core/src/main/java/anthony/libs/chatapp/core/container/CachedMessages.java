package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.message.Message;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by chend on 2017/8/21.
 * 缓存没有找到收件人的消息
 */
public class CachedMessages extends AbstractMapBasedContainer<String, ArrayList<Message>> {
    private static CachedMessages ourInstance = new CachedMessages();
    private MessageQueue messageQueue = MessageQueue.getInstance();

    private CachedMessages() {

    }

    public static CachedMessages getInstance() {
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
        System.out.println("1");
    }


    @Override
    public ArrayList<Message> get(String userId) {
        lock.readLock().lock();
        ArrayList<Message> userMessage = super.get(userId);
        lock.readLock().unlock();
        return userMessage;
    }

    public void sendCachedMessage(String userId) {
        lock.writeLock().lock();
        ArrayList<Message> userMessage = this.get(userId);
        remove(userId);
        lock.writeLock().unlock();
        if (null != userMessage)
            userMessage.forEach((v) -> messageQueue.put(v));
    }


}
