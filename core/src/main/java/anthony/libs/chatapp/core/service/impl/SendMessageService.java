package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.container.CachedMessages;
import anthony.libs.chatapp.core.container.MessageQueue;
import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.message.MessageUtil;
import anthony.libs.chatapp.core.service.AbstractService;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chend on 2017/8/16.
 * 发送消息的服务
 */
public abstract class SendMessageService extends AbstractService {
    private MessageQueue messageQueue = MessageQueue.getInstance();
    private CachedMessages cachedMessages = CachedMessages.getInstance();
    private ExecutorService es;

    protected SendMessageService(int nThreads) {
        super();
        es = Executors.newFixedThreadPool(nThreads);
    }

    @Override
    protected void execute() {
        while (getStatus()) {
            Message message = messageQueue.take();
            if (message == null)
                continue;
            es.submit(new Sender(message));
        }
        es.shutdown();
    }

    public void sendMessage(Message message) {
        messageQueue.putAndWaitReply(message);
    }

    protected abstract SelectionKey getTargetKey(String destination);

    private class Sender implements Runnable {
        private Message message;

        Sender(Message message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                SelectionKey key = getTargetKey(message.getDestination());
                if (null == key) {
//                messageQueue.put(message);
                    cachedMessages.put(message.getDestination(), message);
                    return;
                }
                MessageUtil.sendMessage(message, (SocketChannel) key.channel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
