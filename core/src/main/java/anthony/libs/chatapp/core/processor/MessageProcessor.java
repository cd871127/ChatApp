package anthony.libs.chatapp.core.processor;

import anthony.libs.chatapp.core.message.MessageAndKey;
import anthony.libs.chatapp.core.message.MessageInfo;

/**
 * Created by chend on 2017/8/14.
 */
public interface MessageProcessor {
    void process(MessageInfo messageInfo);
}
