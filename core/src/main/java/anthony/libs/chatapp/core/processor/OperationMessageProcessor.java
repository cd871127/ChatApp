package anthony.libs.chatapp.core.processor;

import anthony.libs.chatapp.core.manager.ConnectionManager;
import anthony.libs.chatapp.core.message.OperationMessage;

/**
 * Created by chend on 2017/8/14.
 */
public class OperationMessageProcessor implements MessageProcessor<OperationMessage> {
    @Override
    public void process(OperationMessage message) {
        System.out.println("OperationMessageProcessor");
        switch (message.getOperation()) {
            case LOGIN:
//                ConnectionManager.getInstance().removeUnLoginSelectionKey(message.getSelectionKey());

                break;
        }
    }
}
