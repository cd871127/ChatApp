package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.container.MessagesWaitReplay;
import anthony.libs.chatapp.core.service.AbstractService;

/**
 * Created by chend on 2017/8/16.
 * 等待响应的消息,超时的就干掉或者重发
 */
public class ReplayMessageService extends AbstractService {

    private MessagesWaitReplay messagesWaitReplay = MessagesWaitReplay.getInstance();

    @Override
    protected void execute() {

    }
}
