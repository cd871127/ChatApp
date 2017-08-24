package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.message.MessageInfo;

/**
 * Created by chend on 2017/8/16.
 * 等待响应的message列表
 */
public class MessagesWaitReplay extends AbstractMapBasedContainer<String, MessageInfo> {
    private static MessagesWaitReplay ourInstance = new MessagesWaitReplay();

    private MessagesWaitReplay() {

    }

    public static MessagesWaitReplay getInstance() {
        return ourInstance;
    }


}
