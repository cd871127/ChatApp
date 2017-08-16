package anthony.libs.chatapp.client;

import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.message.TextMessage;
import anthony.libs.chatapp.core.user.ClientInfo;
import anthony.libs.chatapp.core.user.UserInfo;

import java.util.Scanner;

/**
 * Created by chend on 2017/8/15.
 */
public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        client.setClientInfo(new ClientInfo(new UserInfo("cd871127", "19871127cd")));

        client.login();
        client.clientServiceStart();

        Scanner scanner = new Scanner(System.in);

        String text = null;
        while (!"exit".equals(text)) {
            System.out.println("in put message:");
            text = scanner.nextLine();
            TextMessage textMessage = new TextMessage();
            textMessage.setText(text);
            client.sendMessage(textMessage);
        }

        OperationMessage logout = new OperationMessage();
        logout.setOperation(OperationMessage.TYPE.LOGOUT);
        client.sendMessage(logout);

    }
}
