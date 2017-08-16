package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.message.Message;

/**
 * Created by chend on 2017/8/16.
 */
public class CacheMessageContainer extends AbstractBlockingQueueBasedContainer<Message> {
    private static CacheMessageContainer ourInstance = new CacheMessageContainer();

    public static CacheMessageContainer getInstance() {
        return ourInstance;
    }

    private CacheMessageContainer() {

    }


}
