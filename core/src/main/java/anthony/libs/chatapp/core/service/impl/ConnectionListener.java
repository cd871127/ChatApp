package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.config.SystemConfig;
import anthony.libs.chatapp.core.manager.ConnectionManager;
import anthony.libs.chatapp.core.service.AbstractService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/10.
 * 监听新连接
 */
public class ConnectionListener extends AbstractService {

    private static ConnectionListener ourInstance = new ConnectionListener();

    public static ConnectionListener getInstance() {
        return ourInstance;
    }

    private ConnectionListener() {
        super();
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
                //注册socketChannel
                ConnectionManager.getInstance().registerSocketChannel(socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
