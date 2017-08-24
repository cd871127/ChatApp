package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.container.MessagesWaitReplay;
import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.monitor.Monitor;
import anthony.libs.chatapp.core.service.AbstractService;

import java.util.List;

public abstract class MessageResendService extends AbstractService implements Monitor {
    private MessagesWaitReplay messagesWaitReplay = MessagesWaitReplay.getInstance();
    private SendMessageService sendMessageService;

    public MessageResendService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    private final long maxTime = 5000;
    private long minTime;

    @Override
    public void logInfo() {
        getLogger().info("MessagesWaitReplay: {}", messagesWaitReplay.size());
    }

    @Override
    protected void execute() {
        while (getStatus()) {
            try {
                Thread.sleep(maxTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<MessageInfo> allMessageInfo = messagesWaitReplay.getAll();
            long curTime = System.currentTimeMillis();
            allMessageInfo.forEach((v) -> {
                if (curTime - v.getSendTime() > maxTime) {
                    sendMessageService.sendMessageForReplay(v.getMessage());
                }
            });

        }
    }


    public void addMessage(Message message) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setSendTime(System.currentTimeMillis());
        messageInfo.setMessage(message);
        messagesWaitReplay.put(message.getId(), messageInfo);
    }

    public void removeMessageById(String messageId) {
        messagesWaitReplay.remove(messageId);
    }

}
