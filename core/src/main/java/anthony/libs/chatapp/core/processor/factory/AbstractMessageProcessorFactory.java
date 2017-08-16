package anthony.libs.chatapp.core.processor.factory;

import anthony.libs.chatapp.core.processor.MessageProcessor;

/**
 * Created by chend on 2017/8/14.
 */
public abstract class AbstractMessageProcessorFactory {
    public abstract MessageProcessor getProcessor(String messageClassName);

}
