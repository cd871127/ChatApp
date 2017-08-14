package anthony.libs.chatapp.core.processor;

import anthony.libs.chatapp.core.message.Message;

/**
 * Created by chend on 2017/8/14.
 */
public interface MessageProcessor<V extends Message> {
    void process(V message);
}
