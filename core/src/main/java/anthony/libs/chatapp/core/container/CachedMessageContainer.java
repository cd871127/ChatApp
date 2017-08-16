package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.message.Message;

/**
 * Created by chend on 2017/8/16.
 */
public class CachedMessageContainer extends AbstractMapBasedContainer<String,Message> {
    private static CachedMessageContainer ourInstance = new CachedMessageContainer();

    public static CachedMessageContainer getInstance() {
        return ourInstance;
    }

    private CachedMessageContainer() {

    }


}
