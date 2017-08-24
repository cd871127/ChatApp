package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.container.MessagesWaitForSendQueue;
import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.message.MessageUtil;
import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.service.AbstractService;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chend on 2017/8/16.
 * 发送消息的服务
 */
public abstract class SendMessageService extends AbstractService {
    private MessagesWaitForSendQueue messagesWaitForSendQueue = MessagesWaitForSendQueue.getInstance();
    //    private MessagesWaitReplay messagesWaitReplay = MessagesWaitReplay.getInstance();
    private MessageResendService messageResendService;
    private ExecutorService es;

    protected SendMessageService(int nThreads, MessageResendService messageResendService) {
        super();
        es = Executors.newFixedThreadPool(nThreads);
        this.messageResendService = messageResendService;
    }

    @Override
    protected void execute() {
        while (getStatus()) {
            Message message = messagesWaitForSendQueue.take();
            if (message == null)
                continue;
            SelectionKey key = getTargetKey(message.getDestination());
            if (null == key) {
                //添加message到未发送
                dealNullKey(message);
                continue;
            }
            es.submit(new Sender(message, (SocketChannel) key.channel()));
        }
        es.shutdown();
    }

    public void sendMessage(Message message) {
        messagesWaitForSendQueue.put(message);
    }

    public void sendMessageForReplay(Message message) {
        messagesWaitForSendQueue.put(message);
        messageResendService.addMessage(message);
    }

    public void sendAck(Message message) {
        OperationMessage ack = new OperationMessage();
        ack.setBody(OperationMessage.TYPE.ACK);
        ack.setOneHeader(OperationMessage.CONFIRM_ID, message.getId());
        ack.setDestination(message.getSender());
        ack.setSender(message.getDestination());
        sendMessageForReplay(ack);
    }

    public void sendAckAck(Message message) {
        OperationMessage ackAck = new OperationMessage();
        ackAck.setBody(OperationMessage.TYPE.ACK_ACK);
        ackAck.setDestination(message.getSender());
        ackAck.setOneHeader(OperationMessage.CONFIRM_ID, message.getId());
        ackAck.setSender(message.getDestination());
        sendMessage(ackAck);
    }

    public void confirmReceiveMessage(String messageId) {
        messageResendService.removeMessageById(messageId);
    }

    public static void sendMessageViaSocketChannel(Message message, SocketChannel socketChannel) {
        MessageUtil.sendMessage(message, socketChannel);
    }

    /**
     * 处理空的key
     */
    protected abstract void dealNullKey(Message message);

    protected abstract SelectionKey getTargetKey(String destination);

    private class Sender implements Runnable {
        private Message message;
        private SocketChannel socketChannel;

        Sender(Message message, SocketChannel socketChannel) {
            this.message = message;
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            try {
                sendMessageViaSocketChannel(message, socketChannel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
