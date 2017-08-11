package anthony.libs.chatapp.core.handler;

import anthony.libs.chatapp.core.manager.SelectorManager;
import anthony.libs.chatapp.core.message.MessageUtil;
import anthony.libs.chatapp.core.message.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

/**
 * Created by chend on 2017/8/11.
 */
public class SelectionKeyHandler implements Callable<TextMessage> {
    private SelectionKey selectionKey;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public SelectionKeyHandler(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    @Override
    public TextMessage call() throws Exception {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();



        byte[] t=read(socketChannel,8);
        int headerLength= MessageUtil.byteArrayToInt(t);
        int bodyLength=MessageUtil.byteArrayToInt(t,4);

        byte[] entity=read(socketChannel,headerLength+bodyLength);

        TextMessage message = MessageUtil.decode(entity,headerLength,bodyLength);
        System.out.println(message);
        SelectorManager.getInstance().interestOps(selectionKey, selectionKey.interestOps() | SelectionKey.OP_READ);
        return message;

    }

    private byte[] read(SocketChannel socketChannel, int length) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
        byteBuffer.clear();
        byte[] bytes = new byte[length];
        socketChannel.read(byteBuffer);
        byteBuffer.flip();
        byteBuffer.get(bytes);
        return bytes;
    }

}
