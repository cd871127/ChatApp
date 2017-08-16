package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.message.MessageAndKey;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by chend on 2017/8/14.
 * 保存SelectionKey返回的future
 */
public class MessageAndKeyContainer extends AbstractBlockingQueueBasedContainer<Future<MessageAndKey>> {
    private static MessageAndKeyContainer ourInstance = new MessageAndKeyContainer();

    public static MessageAndKeyContainer getInstance() {
        return ourInstance;
    }

    private MessageAndKeyContainer() {

    }

    private Future<MessageAndKey> getDoneFuture() {

        Future<MessageAndKey> future = null;
        while (future == null) {
            try {
                future = getContainer().take();
                if (!future.isDone()) {
                    addFuture(future);
                    future = null;
                }
            } catch (InterruptedException e) {
                getLogger().error("aaaa");
                e.printStackTrace();
            }
        }
        return future;
    }

    public void addFuture(Future<MessageAndKey> future) {
        try {
            getContainer().put(future);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MessageAndKey getMessageAndKey() {
        MessageAndKey messageAndKey = null;
        try {
            Future<MessageAndKey> future = getDoneFuture();
            if (future != null)
                messageAndKey = future.get();
        } catch (InterruptedException | ExecutionException e) {
            messageAndKey = null;
            e.printStackTrace();
        }
        return messageAndKey;
    }

}
