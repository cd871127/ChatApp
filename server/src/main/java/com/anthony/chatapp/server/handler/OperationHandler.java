package com.anthony.chatapp.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.anthony.chatapp.core.message.CachedMessageService;
import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Operation;
import com.anthony.chatapp.core.message.handler.AbstractOperationHandler;
import com.anthony.chatapp.core.user.UserInfo;
import com.anthony.chatapp.server.user.UserAndKey;
import com.anthony.chatapp.server.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.SelectionKey;

/**
 * Created by chend on 2017/7/1.
 */
public class OperationHandler extends AbstractOperationHandler {
    private static Logger logger = LoggerFactory.getLogger(OperationHandler.class);

    public OperationHandler(MessageAndKey messageAndKey) {
        super(messageAndKey);
    }

    @Override
    public void handle() {
        Message message = messageAndKey.getMessage();
        Operation.OperationType type = ((Operation) message).getOperationType();
        switch (type) {
            case LOGIN:
                logger.info("user " + message.getSender() + " login");
                JSONObject jsonObject = (JSONObject) ((Operation) message).getAttachment();
                UserInfo userInfo = jsonObject.toJavaObject(UserInfo.class);
                UserAndKey userAndKey = new UserAndKey(userInfo, messageAndKey.getKey());
                UserManager.getInstance().userLogin(userAndKey);
                sender.send(new Operation(Operation.OperationType.LOGIN, message.getSender()));
                break;
            case LOGOUT:
                logger.info("user " + message.getSender() + " logout");
                UserManager.getInstance().userLogout(message.getSender());
                sender.send(new Operation(Operation.OperationType.LOGOUT, message.getSender()));
                break;
            case ACK:
                sendAckAck(message);
                break;
            case ACKACK:
                CachedMessageService.getInstance().removeMessage(((Operation)message).getAttachment().toString());
                break;
            case LC:
                SelectionKey key = messageAndKey.getKey();
                UserManager.getInstance().removeUserByKey(key);
                break;
        }
    }
}
