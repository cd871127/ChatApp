package anthony.libs.chatapp.core.handler;

import anthony.libs.chatapp.core.message.MessageInfo;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/22.
 */
public abstract class MessageHandler extends NewDataHandler {
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
        } catch (IOException e) {
            exceptionHandle(e);
        }
        return messageInfo;
    }

    protected abstract void exceptionHandle(Exception e);

    protected SelectionKey getSelectionKey() {
        return selectionKey;
    }
}
