package anthony.libs.chatapp.client;

import anthony.libs.chatapp.core.config.SystemConfig;
import anthony.libs.chatapp.core.manager.ConnectionManager;
import anthony.libs.chatapp.core.manager.ServiceManager;
import anthony.libs.chatapp.core.message.Message;
import anthony.libs.chatapp.core.message.MessageUtil;
import anthony.libs.chatapp.core.message.OperationMessage;
import anthony.libs.chatapp.core.service.impl.MessageListener;
import anthony.libs.chatapp.core.service.impl.MessageProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/11.
 */
public class Client {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ClientInfo clientInfo;
    private SocketChannel socketChannel;
    private ServiceManager serviceManager;
    private ConnectionManager connectionManager;
    private MessageProcessService messageProcessService;
    private MessageListener messageListener;

    public Client() {
        serviceManager = new ServiceManager(2);
        messageProcessService = MessageProcessService.getInstance();
        messageListener = MessageListener.getInstance();
        connectionManager = ConnectionManager.getInstance();
    }

    public void clientServiceStart() {
        serviceManager = new ServiceManager(6);
        serviceManager.registerService(messageProcessService);
        serviceManager.registerService(messageListener);
        serviceManager.start();
    }

    private void connect() throws IOException {
        socketChannel = SocketChannel.open();
        InetAddress server = InetAddress.getLocalHost();
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
        connectionManager.registerSocketChannel(socketChannel);
        OperationMessage login = new OperationMessage();
        login.setOperation(OperationMessage.TYPE.LOGIN);

        login.setSender(clientInfo.getUserInfo().getUserId());
        login.setOneHeader("USER_INFO", clientInfo.getUserInfo().toString());
        sendMessage(login);
        return true;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public void sendMessage(Message message) {
        MessageUtil.sendMessage(message, socketChannel);
    }

    public void stop() {
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
