package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.service.AbstractRunnableService;

/**
 * Created by chend on 2017/8/10.
 */
public class MessageListener extends AbstractRunnableService {
    private static MessageListener ourInstance = new MessageListener();

    public static MessageListener getInstance() {
        return ourInstance;
    }

    private MessageListener() {
    }

    @Override
    public void run() {

    }
}
