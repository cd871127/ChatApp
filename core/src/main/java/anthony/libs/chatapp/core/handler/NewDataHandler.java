package anthony.libs.chatapp.core.handler;

import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.message.MessageUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/22.
 */
public abstract class NewDataHandler extends AbstractHandler<MessageInfo> {

    protected MessageInfo getMessageInfo(SocketChannel socketChannel) throws IOException {
        Message message;
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setSocketChannel(socketChannel);
        byte[] t = read(socketChannel, 8);
        int headerLength = MessageUtil.byteArrayToInt(t);
        int bodyLength = MessageUtil.byteArrayToInt(t, 4);
        byte[] entity = read(socketChannel, headerLength + bodyLength);
        message = MessageUtil.decode(entity, headerLength, bodyLength);
        messageInfo.setMessage(message);
        return messageInfo;
    }


}
