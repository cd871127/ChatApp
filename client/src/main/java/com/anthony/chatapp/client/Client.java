package com.anthony.chatapp.client;

import com.anthony.chatapp.core.protocol.constant.Operations;
import com.anthony.chatapp.core.protocol.message.Operation;
import com.anthony.chatapp.core.protocol.message.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/6/27.
 */
public class Client {
    public static void main(String[] args) throws IOException {

socket();
//        channel();
    }


    public static void channel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        InetAddress localhost = InetAddress.getLocalHost();
        socketChannel.connect(new InetSocketAddress(localhost, 51127));
        while (!socketChannel.isConnected()) {
            System.out.println("wait");
        }
        Text Text = new Text();
        Text.setFrom("client");
        Text.setTo("client");
        Text.setTextBody("123");
        byte[] tmp=Text.encode();
        ByteBuffer byteBuffer=ByteBuffer.allocateDirect(tmp.length);
        byteBuffer.clear();
        byteBuffer.put(tmp);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        while (true);
    }

    public static void socket() throws IOException {
        Socket socket = new Socket();
        InetAddress localhost = InetAddress.getLocalHost();
        socket.connect(new InetSocketAddress(localhost, 51127));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String tmp;
//        PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        BufferedReader recieveFromServ = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //send login
        Operation login = new Operation(Operations.USER_LOGIN);

        dos.write(login.encode());

        Text Text = new Text();
        Text.setFrom("client");
        Text.setTo("client");
        while ((tmp = bufferedReader.readLine()) != null && (!"quit".equals(tmp))) {
            Text.setTextBody(tmp);
            dos.write(Text.encode());
            System.out.println("**************");
            while ((tmp = recieveFromServ.readLine()) != null && (!"quit".equals(tmp))) {
                System.out.println(tmp);
            }
            System.out.println("**************");
//            dos.flush();
        }
//        writer.close();

        Operation logout = new Operation(Operations.USER_LOGOUT);

        dos.write(logout.encode());

        dos.close();
        bufferedReader.close();
        socket.close();
    }
}
