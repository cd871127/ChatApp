package com.anthony.chatapp.server.user;

import com.anthony.chatapp.core.protocol.message.Message;
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
//            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
//            Message message=parse(dataInputStream);
            Message message;
            do {
                message = Message.decode(socket.getInputStream());
                System.out.println(message);
            }while (true);


//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    private Message parse(DataInputStream dataInputStream) throws IOException {
        byte[] data = new byte[Message.HEADER_LENGTH];
        Message message = new Message();
        dataInputStream.read(data, 0, Message.HEADER_LENGTH);
//        Message.decodeHeaders(data, message);
        int length = message.getBodyLength();
        byte[] body = new byte[length];
        dataInputStream.read(body);
//        Message.decodeBody(body, message);
        return message;
    }

    @Override
    public void run() {
        execute();
    }
}
