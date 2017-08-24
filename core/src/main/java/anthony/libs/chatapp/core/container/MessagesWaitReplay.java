package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.message.MessageInfo;

/**
 * Created by chend on 2017/8/16.
 * 等待响应的message列表
 */
public class MessagesWaitReplay extends AbstractMapBasedContainer<String, Message> {
    private static MessagesWaitReplay ourInstance = new MessagesWaitReplay();

    public static MessagesWaitReplay getInstance() {
        return ourInstance;
    }

    private MessagesWaitReplay() {

    }


}
