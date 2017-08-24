package anthony.libs.chatapp.client;

import anthony.libs.chatapp.client.processor.factory.ClientMessageProcessorFactory;
import anthony.libs.chatapp.client.service.ClientMessageListener;
import anthony.libs.chatapp.client.service.ClientSendMessageService;
import anthony.libs.chatapp.core.config.SystemConfig;
import anthony.libs.chatapp.core.manager.ConnectionManager;
import anthony.libs.chatapp.core.manager.ServiceManager;
import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.service.impl.MessageListener;
import anthony.libs.chatapp.core.service.impl.MessageProcessService;
import anthony.libs.chatapp.core.user.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/11.
 */
public class Client {
    private static Client ourInstance = new Client();

    public static Client getInstance() {
        return ourInstance;
    }


    private UserInfo userInfo;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SocketChannel socketChannel;
    private ServiceManager serviceManager;
    private ConnectionManager connectionManager;
    private MessageProcessService messageProcessService;
    private MessageListener messageListener;
    private ClientSendMessageService clientSendMessageService;
    private static SelectionKey selectionKey;

    private Client() {
        serviceManager = new ServiceManager(2);
        messageProcessService = MessageProcessService.getInstance();
        messageProcessService.setMessageProcessorFactory(new ClientMessageProcessorFactory());
        messageListener = ClientMessageListener.getInstance();
        connectionManager = ConnectionManager.getInstance();
        clientSendMessageService = ClientSendMessageService.getInstance();

    }

    public void clientServiceStart() {
        serviceManager = new ServiceManager(6);
        serviceManager.registerService(messageProcessService);
        serviceManager.registerService(messageListener);
        serviceManager.registerService(clientSendMessageService);
        serviceManager.start();
    }

    private void connect() throws IOException {
        socketChannel = SocketChannel.open();
        InetAddress server = InetAddress.getLocalHost();
//        InetAddress server = InetAddress.getByName("app-1.anthony");
        socketChannel.connect(new InetSocketAddress(server, SystemConfig.SERVER_PORT));
    }

    public boolean login() {
        try {
            connect();
        } catch (IOException e) {
            logger.error("链接服务器失败");
            e.printStackTrace();
            return false;
        }
        selectionKey = connectionManager.registerSocketChannel(socketChannel);
        OperationMessage login = new OperationMessage();
        login.setOperation(OperationMessage.TYPE.LOGIN);

        login.setSender(userInfo.getUserId());
        login.setOneHeader("USER_INFO", userInfo.toString());
        sendMessage(login);
        return true;
    }

    public void setClientInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void sendMessage(Message message) {
        clientSendMessageService.sendMessage(message);
    }

    public void stop() {
        try {
            serviceManager.stop();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SelectionKey getSelectionKey() {
        return selectionKey;
    }
}
