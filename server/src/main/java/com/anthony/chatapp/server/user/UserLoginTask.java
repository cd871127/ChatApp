package com.anthony.chatapp.server.user;

import com.anthony.chatapp.core.protocol.Message;
import com.anthony.chatapp.core.protocol.TextMessage;
import com.anthony.chatapp.core.service.Task;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by chend on 2017/6/28.
 */
public class UserLoginTask implements Task {
    private Socket socket;

    public UserLoginTask(Socket socket) {
        this.socket = socket;
    }

    private void execute() {
        UserContainer container = UserContainer.getInstance();
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
//            ByteBuffer buffer=ByteBuffer.allocate(Message.HEADER_LENGTH);
            byte[] data = new byte[Message.HEADER_LENGTH];
            Message message = new Message();
            dataInputStream.read(data);
            Message.decodeHeaders(data, message);
            System.out.println(message);
            int length = message.getBodyLength();
            System.out.println(length);
            byte[] body = new byte[length];
            dataInputStream.read(body);
            Message.decodeBody(body, message);
            System.out.println(message);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        execute();
    }
}
