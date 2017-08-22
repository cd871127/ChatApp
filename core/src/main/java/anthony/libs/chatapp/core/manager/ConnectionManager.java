package anthony.libs.chatapp.core.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by chend on 2017/8/11.
 */
public class ConnectionManager {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Selector selector;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private static ConnectionManager ourInstance = new ConnectionManager();

    //连接数量
    private final static int CON_NUM = 2;

    public static ConnectionManager getInstance() {
        return ourInstance;
    }


    private ConnectionManager() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            logger.error("打开selector失败");
            e.printStackTrace();
        }
    }

    public SelectionKey registerSocketChannel(SocketChannel socketChannel) {
        logger.debug("add channel");
        SelectionKey key;
        try {
            socketChannel.configureBlocking(false);
            if (socketChannel.isConnectionPending())
                socketChannel.finishConnect();
            lock.writeLock().lock();
            selector.wakeup();
            key = socketChannel.register(selector, SelectionKey.OP_READ);
            lock.writeLock().unlock();

        } catch (IOException e) {
            key = null;
            logger.error("注册channel失败");
            e.printStackTrace();
        }
        return key;
    }

    public Set<SelectionKey> getReadableKeys() {
        if (lock.isWriteLocked())
            return null;
        try {
            int readyKeys = selector.select();
            if (readyKeys == 0)
                return null;
        } catch (IOException e) {
            logger.error("selector.select() 失败");
            e.printStackTrace();
        }
        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
        Set<SelectionKey> readableKeys = new HashSet<>();
        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            if (key.isReadable()) {
                //设置对读没兴趣 以防重复处理key
                key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
                readableKeys.add(key);
            }
            keyIterator.remove();
        }
        return readableKeys.isEmpty() ? null : readableKeys;
    }

    public void interestOps(SelectionKey key, int ops) {
        lock.writeLock().lock();
        selector.wakeup();
        key.interestOps(ops);
        lock.writeLock().unlock();
    }

//    public void removeUnLoginSelectionKey(SelectionKey key) {
//        UnLoginSelectionKeyContainer.getInstance().remove(key.toString());
//        try {
//            key.channel().close();
//            key.cancel();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}