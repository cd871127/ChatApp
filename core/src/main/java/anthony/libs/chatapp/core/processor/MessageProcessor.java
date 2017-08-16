package anthony.libs.chatapp.core.processor;

import anthony.libs.chatapp.core.message.MessageAndKey;

/**
 * Created by chend on 2017/8/14.
 */
public interface MessageProcessor {
    void process(MessageAndKey messageAndKey);
}
