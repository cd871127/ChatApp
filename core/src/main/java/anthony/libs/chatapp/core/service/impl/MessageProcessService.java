package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.container.MessageContainer;
import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.processor.MessageProcessor;
import anthony.libs.chatapp.core.processor.factory.MessageProcessorFactory;
import anthony.libs.chatapp.core.service.AbstractService;

/**
 * Created by chend on 2017/8/14.
 * 分配消息
 */
public class MessageProcessService extends AbstractService {
    private static MessageProcessService ourInstance = new MessageProcessService();

    public static MessageProcessService getInstance() {
        return ourInstance;
    }

    private MessageProcessService() {
        super();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void execute() {
        while (getStatus()) {
            Message message = MessageContainer.getInstance().getMessage();

            MessageProcessor messageProcessor = MessageProcessorFactory.getProcessor(message.getClass().getName());
            //转发消息
            if (null != messageProcessor)
                messageProcessor.process(message);
        }
    }
}
