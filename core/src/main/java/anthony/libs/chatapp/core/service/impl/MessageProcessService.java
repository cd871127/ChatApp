package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.container.MessageAndKeyContainer;
import anthony.libs.chatapp.core.message.MessageAndKey;
import anthony.libs.chatapp.core.processor.MessageProcessor;
import anthony.libs.chatapp.core.processor.factory.AbstractMessageProcessorFactory;
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

    private MessageAndKeyContainer messageContainer = MessageAndKeyContainer.getInstance();

    private AbstractMessageProcessorFactory messageProcessorFactory;

    private MessageProcessService() {
        super();
    }

    public void setMessageProcessorFactory(AbstractMessageProcessorFactory messageProcessorFactory) {
        this.messageProcessorFactory = messageProcessorFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void execute() {
        while (getStatus()) {
            MessageAndKey messageAndKey = messageContainer.getMessageAndKey();

            MessageProcessor messageProcessor=null;
            if(messageAndKey!=null)
                messageProcessor = messageProcessorFactory.getProcessor(messageAndKey.getMessage().getClass().getName());
            //转发消息
            if (null != messageProcessor)
                messageProcessor.process(messageAndKey);
        }
    }
}
