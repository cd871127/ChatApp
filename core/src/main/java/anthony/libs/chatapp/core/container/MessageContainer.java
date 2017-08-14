package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.message.Message;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by chend on 2017/8/14.
 * 保存SelectionKey返回的future
 */
public class MessageContainer extends AbstractBlockingQueueBasedContainer<Future<Message>> {
    private static MessageContainer ourInstance = new MessageContainer();

    public static MessageContainer getInstance() {
        return ourInstance;
    }

    private MessageContainer() {

    }

    private Future<Message> getDoneFuture() {

        Future<Message> future = null;
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

    public void addFuture(Future<Message> future) {
        try {
            getContainer().put(future);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Message getMessage() {
        Message message=null;
        try {
            Future<Message> future = getDoneFuture();
            if (future != null)
                message = future.get();
        } catch (InterruptedException | ExecutionException e) {
            message = null;
            e.printStackTrace();
        }
        return message;
    }

}
