package anthony.libs.chatapp.core.container;

import anthony.libs.chatapp.core.message.MessageInfo;

/**
 * Created by chend on 2017/8/22.
 * baocun jiexide xiaoxi
 */
public class MessageInfoFutureList extends FutureList<MessageInfo> {
    private static MessageInfoFutureList ourInstance = new MessageInfoFutureList();

    private MessageInfoFutureList() {
    }

    public static MessageInfoFutureList getInstance() {
        return ourInstance;
    }


}
