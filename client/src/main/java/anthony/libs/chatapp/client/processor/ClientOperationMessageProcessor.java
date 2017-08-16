package anthony.libs.chatapp.client.processor;

import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.processor.AbstractOperationMessageProcessor;

/**
 * Created by chend on 2017/8/14.
 */
public class ClientOperationMessageProcessor extends AbstractOperationMessageProcessor {
    @Override
    public void doProcess(OperationMessage message) {
        System.out.println("OperationMessageProcessor");
        switch (message.getOperation()) {
            case LOGIN:
//                ConnectionManager.getInstance().removeUnLoginSelectionKey(message.getSelectionKey());

                break;
        }
    }
}
