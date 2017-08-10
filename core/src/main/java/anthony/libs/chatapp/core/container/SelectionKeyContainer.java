package anthony.libs.chatapp.core.container;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by chend on 2017/8/10.
 */
public class SelectionKeyContainer extends AbstractMapBasedContainer<String, SelectionKey> {
    private static SelectionKeyContainer ourInstance = new SelectionKeyContainer();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Selector selector;

    public static SelectionKeyContainer getInstance() {
        return ourInstance;
    }

    private SelectionKeyContainer() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            getLogger().error("打开selector失败");
            e.printStackTrace();
        }
    }

    public SelectionKey registerSocketChannel(SocketChannel socketChannel) {
        getLogger().debug("add channel");
        SelectionKey key;
        try {
            socketChannel.configureBlocking(false);
            if (socketChannel.isConnectionPending())
                socketChannel.finishConnect();
            lock.writeLock().lock();
            selector.wakeup();
            key = socketChannel.register(selector, SelectionKey.OP_READ);
            lock.writeLock().unlock();
            put("", key);
        } catch (IOException e) {
            key = null;
            getLogger().error("注册channel失败");
            e.printStackTrace();
        }
        return key;
    }


}
