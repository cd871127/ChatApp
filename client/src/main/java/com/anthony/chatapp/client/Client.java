package com.anthony.chatapp.client;

import com.anthony.chatapp.core.protocol.message.Message;
import com.anthony.chatapp.core.system.Parameters;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by chend on 2017/6/27.
 */
public class Client {

    private static SocketChannel socketChannel;


    public static void main(String[] args) throws IOException, InterruptedException {
        socketChannel = SocketChannel.open();
        InetAddress localhost = InetAddress.getLocalHost();
        socketChannel.connect(new InetSocketAddress(localhost, 51127));
        while (!socketChannel.isConnected()) {
            System.out.println("wait");
        }


        Thread t1 = new Thread(Client::channel);

        t1.start();

        Thread t2 = new Thread(Client::read);
        t2.start();
    }


    private static void read() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1);
        byteBuffer.clear();
        byteBuffer.flip();
        byte[] b = new byte[1];
        try {
            while ((socketChannel.read(byteBuffer)) != -1) {
                byteBuffer.flip();
                byteBuffer.get(b);
                System.out.print(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void channel() {
        try {

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8192);
            int i = 0;
            Scanner scanner = new Scanner(System.in);

            System.out.println("input your name:");
            Parameters.SENDER = scanner.nextLine();
            System.out.println("input receiver:");
            String receiver = scanner.nextLine();

            Message loginInfo = new Message.MessageBuilder("server", Message.Operations.LOGIN, String.valueOf(System.currentTimeMillis())).build();

            byteBuffer.clear();
            byteBuffer.put(loginInfo.encode());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);

            while (scanner.hasNext()) {
                Message message = new Message.MessageBuilder(receiver, scanner.next(), String.valueOf(System.currentTimeMillis())).build();

                byteBuffer.clear();
                byteBuffer.put(message.encode());
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                System.out.println("send: " + (++i));
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//    public static void socket() throws IOException {
//        Socket socket = new Socket();
//        InetAddress localhost = InetAddress.getLocalHost();
//        socket.connect(new InetSocketAddress(localhost, 51127));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        String tmp;
//        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//
//        BufferedReader recieveFromServ = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//        //send login
//        Operation login = new Operation(Operations.USER_LOGIN);
//
//        dos.write(login.encode());
//
//        Text Text = new Text();
//        Text.setFrom("client");
//        Text.setTo("client");
//        while ((tmp = bufferedReader.readLine()) != null && (!"quit".equals(tmp))) {
//            Text.setTextBody(tmp);
//            dos.write(Text.encode());
//            System.out.println("**************");
//            while ((tmp = recieveFromServ.readLine()) != null && (!"quit".equals(tmp))) {
//                System.out.println(tmp);
//            }
//            System.out.println("**************");
////            dos.flush();
//        }
////        writer.close();
//
//        Operation logout = new Operation(Operations.USER_LOGOUT);
//
//        dos.write(logout.encode());
//
//        dos.close();
//        bufferedReader.close();
//        socket.close();
//    }
}
