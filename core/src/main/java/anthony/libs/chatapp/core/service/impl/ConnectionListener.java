package anthony.libs.chatapp.core.service.impl;

import anthony.libs.chatapp.core.container.SelectionKeyContainer;
import anthony.libs.chatapp.core.service.AbstractRunnableService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by chend on 2017/8/10.
 * 监听新连接
 */
public class ConnectionListener extends AbstractRunnableService {

    @Override
    public void run() {
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(1234));
        } catch (IOException e) {
            setStatus(false);
            getLogger().error("创建ServerSocketChannel失败");
            e.printStackTrace();
        }
        try {
            getLogger().info("开始监听端口:" + 1234);
            while (getStatus()) {
                assert serverSocketChannel != null;
                SocketChannel socketChannel = serverSocketChannel.accept();
                getLogger().info("new connection: " + socketChannel.getRemoteAddress().toString());
                //注册socketChannel
                SelectionKeyContainer.getInstance().registerSocketChannel(socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
