package anthony.libs.chatapp.client;

import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.message.TextMessage;
import anthony.libs.chatapp.core.user.UserInfo;

import java.util.Scanner;

/**
 * Created by chend on 2017/8/15.
 */
public class Main {
    private static boolean AUTO_INPUT = false;

    public static void main(String[] args) throws InterruptedException {
        String text;

        System.out.println("input username:");
        if (AUTO_INPUT) {
            text = "test";
        } else {
            Scanner scanner = new Scanner(System.in);
            text = scanner.nextLine();
        }
        Client client = Client.getInstance();
        UserInfo userInfo = new UserInfo(text, "19871127cd");
        userInfo.setUserId(text);
        client.setClientInfo(userInfo);

        client.login();
        client.clientServiceStart();
        int i = 0;
        while (!"exit".equals(text)) {
            System.out.println("in put message:(target:message)");
            if (AUTO_INPUT) {
                text = "test:" + (i++);
                Thread.sleep(5000);
            } else {
                Scanner scanner = new Scanner(System.in);
                text = scanner.nextLine();
                if (!text.contains(":"))
                    continue;
            }
            String[] tmp = text.split(":");

            TextMessage textMessage = new TextMessage();
            textMessage.setDestination(tmp[0]);
            textMessage.setText(tmp[1]);
            textMessage.setSender(userInfo.getUserId());
            client.sendMessage(textMessage);
        }

        OperationMessage logout = new OperationMessage();
        logout.setOperation(OperationMessage.TYPE.LOGOUT);
        client.sendMessage(logout);

    }
}
