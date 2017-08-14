package anthony.libs.chatapp.client;

import anthony.libs.chatapp.core.config.SystemConfig;
import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.message.MessageUtil;
import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.message.TextMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by chend on 2017/8/11.
 */
public class Client {
    public static void main(String[] args) throws InterruptedException, IOException {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            InetAddress server = InetAddress.getLocalHost();
            socketChannel.connect(new InetSocketAddress(server, SystemConfig.SERVER_PORT));

//            TextMessage t = new TextMessage();
//            t.setText("123123123");
            OperationMessage login = new OperationMessage();
            login.setOperation(OperationMessage.TYPE.LOGIN);
            sendMessage(login, socketChannel);


            Scanner scanner = new Scanner(System.in);
            String text = null;
            while (!"exit".equals(text)) {
                System.out.println("in put message:");
                text = scanner.nextLine();
                TextMessage textMessage = new TextMessage();
                textMessage.setText(text);
                sendMessage(textMessage, socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socketChannel != null) {
                OperationMessage logout = new OperationMessage();
                logout.setOperation(OperationMessage.TYPE.LOGOUT);
                sendMessage(logout, socketChannel);
                socketChannel.close();
            }

        }
        while (true)
            ;
    }

    public static void sendMessage(Message message, SocketChannel socketChannel) throws IOException {
        byte[] messageByte = MessageUtil.encode(message);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(messageByte.length);
        byteBuffer.put(messageByte);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }
}
