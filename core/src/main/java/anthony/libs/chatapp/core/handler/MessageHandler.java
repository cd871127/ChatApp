package anthony.libs.chatapp.core.handler;

import anthony.libs.chatapp.core.manager.ConnectionManager;
import anthony.libs.chatapp.core.message.MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/22.
 */
public abstract class MessageHandler extends NewDataHandler {
    private static ConnectionManager connectionManager = ConnectionManager.getInstance();

    private Logger logger = LoggerFactory.getLogger(getClass());

    private SelectionKey selectionKey;

    public MessageHandler(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    @Override
    public MessageInfo handle() {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        MessageInfo messageInfo = null;
        try {
            messageInfo = getMessageInfo(socketChannel);
        } catch (Exception e) {
            exceptionHandle(e);
        }
        if (selectionKey.isValid())
            connectionManager.interestOps(selectionKey, selectionKey.interestOps() | SelectionKey.OP_READ);
        return messageInfo;
    }

    protected abstract void exceptionHandle(Exception e);

    protected SelectionKey getSelectionKey() {
        return selectionKey;
    }
}
