package com.anthony.chatapp.server.handler;

import com.anthony.chatapp.core.message.MessageAndKey;
import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.message.entity.Operation;
import com.anthony.chatapp.core.message.handler.AbstractMessageHandler;
import com.anthony.chatapp.core.user.UserInfo;
import com.anthony.chatapp.server.user.UserAndKey;
import com.anthony.chatapp.server.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chend on 2017/7/1.
 */
public class OperationHandler extends AbstractMessageHandler {
    private static Logger logger = LoggerFactory.getLogger(OperationHandler.class);

    public OperationHandler(MessageAndKey messageAndKey) {
        super(messageAndKey);
    }

    @Override
    public void handle() {
        Message message = messageAndKey.getMessage();
        Operation.OperationTypes type = ((Operation) message.getAttachment()).getOperationType();
        switch (type) {
            case LOGIN:
                logger.info("user " + message.getSender() + " login");

                UserInfo userInfo= (UserInfo) ((Operation)(message.getAttachment())).getAttachment();
                UserAndKey userAndKey=new UserAndKey(userInfo,messageAndKey.getKey());
                UserManager.getInstance().userLogin(userAndKey);
                break;
            case LOGOUT:
                logger.info("user logout");
                break;
        }
    }
}
