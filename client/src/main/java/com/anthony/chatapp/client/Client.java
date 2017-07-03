package com.anthony.chatapp.client;

import com.anthony.chatapp.core.message.entity.Message;
import com.anthony.chatapp.core.system.Parameters;

import java.util.Scanner;

/**
 * Created by chend on 2017/6/27.
 */
public class Client {

    public static void main(String[] args) {
        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("input your name:");
            Parameters.SENDER = scanner.nextLine();
        }
        ClientService clientService = new ClientService();
        clientService.start();

        if (!UserController.isIsLogin()) {
            System.out.println("log error");
            return;
        }
        /**command ui**/
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String[] tmp = scanner.nextLine().split(":");
            Message message = new Message.MessageBuilder(tmp[1], tmp[0]).build();
            ClientMessageSender.getInstance().send(message);
        }
    }


//    private static void channel() {
//        try {
//
//            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8192);
//            int i = 0;
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("input your name:");
//            Parameters.SENDER = scanner.nextLine();
//            System.out.println("input receiver:");
//            String receiver = scanner.nextLine();
//
//            Message message = new Message.MessageBuilder(Operation.OperationTypes.LOGIN, "server").build();
//
//            MessageSender ms = new MessageSender();
//            ms.send(message, socketChannel);
//
//            while (scanner.hasNext()) {
//                message = new Message.MessageBuilder(scanner.nextLine(), "server").build();
//
//                ms.send(message, socketChannel);
//                System.out.println("send: " + (++i));
//                System.out.println(message);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//    }

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
