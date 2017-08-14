package anthony.libs.chatapp.core.handler;

import anthony.libs.chatapp.core.manager.ConnectionManager;
import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.message.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/11.
 * 读取SelectionKey中的message
 */
public class SelectionKeyHandler extends AbstractHandler<Message> {
    private SelectionKey selectionKey;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public SelectionKeyHandler(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    @Override
    public Message handle() {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        Message message;
        try {
            byte[] t = read(socketChannel, 8);
            int headerLength = MessageUtil.byteArrayToInt(t);
            int bodyLength = MessageUtil.byteArrayToInt(t, 4);
            byte[] entity = read(socketChannel, headerLength + bodyLength);
            message = MessageUtil.decode(entity, headerLength, bodyLength);
            if (null != message)
                message.setSelectionKey(selectionKey);
            ConnectionManager.getInstance().interestOps(selectionKey, selectionKey.interestOps() | SelectionKey.OP_READ);
        } catch (IOException | BufferUnderflowException e) {
            logger.error("失去链接");
            e.printStackTrace();
            message = null;
        }
        return message;
    }

    private byte[] read(SocketChannel socketChannel, int length) throws IOException, BufferUnderflowException {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
        byteBuffer.clear();
        byte[] bytes = new byte[length];
        socketChannel.read(byteBuffer);
        byteBuffer.flip();
        byteBuffer.get(bytes);
        return bytes;
    }

}
