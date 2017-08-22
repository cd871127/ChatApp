package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.message.MessageInfo;

/**
 * Created by chend on 2017/8/22.
 */
public class MessageInfoFutureList extends FutureList<MessageInfo> {
    private static MessageInfoFutureList ourInstance = new MessageInfoFutureList();

    public static MessageInfoFutureList getInstance() {
        return ourInstance;
    }

    private MessageInfoFutureList() {
    }


}
