package anthony.libs.chatapp.client;

import anthony.libs.chatapp.core.config.SystemConfig;
import anthony.libs.chatapp.core.message.MessageUtil;
import anthony.libs.chatapp.core.message.TextMessage;
import com.anthony.chatapp.client.ClientParameter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/11.
 */
public class Client {
    public static void main(String[] args) {
        SocketChannel socketChannel;
        try {
            socketChannel = SocketChannel.open();
            InetAddress server = InetAddress.getLocalHost();
            socketChannel.connect(new InetSocketAddress(server, SystemConfig.port));

            TextMessage t=new TextMessage();
            t.setText("123123123");

            byte[] messageByte = MessageUtil.encode(t);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(messageByte.length);
            byteBuffer.put(messageByte);
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
