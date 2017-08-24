package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.container.MessageInfoFutureList;
import anthony.libs.chatapp.core.message.MessageInfo;
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

//    private MessageAndKeyContainer messageContainer = MessageAndKeyContainer.getInstance();

    private MessageInfoFutureList messageInfoFutureList=MessageInfoFutureList.getInstance();
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
//            MessageAndKey messageAndKey = messageContainer.getMessageAndKey();
            MessageInfo messageInfo=messageInfoFutureList.getElement();
            MessageProcessor messageProcessor=null;
            if(messageInfo!=null)
                messageProcessor = messageProcessorFactory.getProcessor(messageInfo.getMessage().getClass().getName());
            //转发消息
            if (null != messageProcessor)
                messageProcessor.process(messageInfo);
        }
    }
}
