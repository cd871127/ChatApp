package anthony.libs.chatapp.client;

import anthony.libs.chatapp.core.config.SystemConfig;
import anthony.libs.chatapp.core.message.MessageUtil;
import anthony.libs.chatapp.core.message.OperationMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/11.
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
        SocketChannel socketChannel;
        try {
            socketChannel = SocketChannel.open();
            InetAddress server = InetAddress.getLocalHost();
            socketChannel.connect(new InetSocketAddress(server, SystemConfig.port));

//            TextMessage t = new TextMessage();
//            t.setText("123123123");
            OperationMessage t=new OperationMessage();
            t.setOperation(OperationMessage.TYPE.LOGIN);
            byte[] messageByte = MessageUtil.encode(t);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(messageByte.length);
            byteBuffer.put(messageByte);
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            System.out.println(t.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
