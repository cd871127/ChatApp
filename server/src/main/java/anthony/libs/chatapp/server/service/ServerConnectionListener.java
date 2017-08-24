package anthony.libs.chatapp.server.service;

import anthony.libs.chatapp.core.config.SystemConfig;
import anthony.libs.chatapp.core.message.MessageInfo;
import anthony.libs.chatapp.core.service.AbstractService;
import anthony.libs.chatapp.server.handler.NewConnectionHandler;
import anthony.libs.chatapp.server.manager.ClientManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by chend on 2017/8/10.
 * 监听新连接
 */
public class ServerConnectionListener extends AbstractService {

    private static ServerConnectionListener ourInstance = new ServerConnectionListener();
    private ExecutorService es;
    private ClientManager clientManager;

    private ServerConnectionListener() {
        super();
        es = Executors.newFixedThreadPool(3);
        clientManager = ClientManager.getInstance();
    }

    public static ServerConnectionListener getInstance() {
        return ourInstance;
    }

    @Override
    protected void execute() {
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(SystemConfig.SERVER_PORT));
        } catch (IOException e) {
            setStatus(false);
            getLogger().error("创建ServerSocketChannel失败");
            e.printStackTrace();
        }
        try {
            getLogger().info("开始监听端口:" + SystemConfig.SERVER_PORT);
            while (getStatus()) {
                assert serverSocketChannel != null;
                SocketChannel socketChannel = serverSocketChannel.accept();
                getLogger().info("new connection: " + socketChannel.getRemoteAddress().toString());
                //新线程等待用户的登陆信息
                Future<MessageInfo> loginInfo = es.submit(new NewConnectionHandler(socketChannel));
                clientManager.clientLogin(loginInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        es.shutdown();
    }
}
