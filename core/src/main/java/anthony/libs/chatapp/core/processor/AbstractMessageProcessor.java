package anthony.libs.chatapp.core.processor;

import anthony.libs.chatapp.core.message.MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chend on 2017/8/16.
 */
public abstract class AbstractMessageProcessor implements MessageProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract void doProcess(MessageInfo messageInfo);

    @Override
    public void process(MessageInfo messageInfo) {
        doProcess(messageInfo);
    }

    public Logger getLogger() {
        return logger;
    }
}
