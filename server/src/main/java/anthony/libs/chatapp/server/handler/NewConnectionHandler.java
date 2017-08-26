package anthony.libs.chatapp.server.handler;

import anthony.libs.chatapp.core.handler.NewDataHandler;
import anthony.libs.chatapp.core.message.MessageInfo;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/22.
 * 从SocketChannel里面读取Message
 */
public class NewConnectionHandler extends NewDataHandler {
    private SocketChannel socketChannel;

    public NewConnectionHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public MessageInfo handle() {
        try {
            return getMessageInfo(socketChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
