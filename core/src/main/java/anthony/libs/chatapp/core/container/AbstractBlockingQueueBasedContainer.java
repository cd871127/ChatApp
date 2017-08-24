package anthony.libs.chatapp.core.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by chend on 2017/8/14.
 */
public class AbstractBlockingQueueBasedContainer<V> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Logger getLogger() {
        return logger;
    }

    private BlockingQueue<V> container = new LinkedBlockingQueue<>();

    protected BlockingQueue<V> getContainer() {
        return container;
    }

    public boolean add(V e) {
        return container.add(e);
    }

    public boolean offer(V e) {
        return container.offer(e);
    }

    public void put(V e) throws InterruptedException {
        container.put(e);
    }

    public boolean offer(V e, long timeout, TimeUnit unit) throws InterruptedException {
        return container.offer(e, timeout, unit);
    }

    public V take() throws InterruptedException {
        return container.take();
    }

    public V poll(long timeout, TimeUnit unit) throws InterruptedException {
        return container.poll(timeout, unit);
    }

    public boolean remove(V o) {
        return container.remove(o);
    }

    public boolean contains(V o) {
        return container.contains(o);
    }

    public int size() {
        return container.size();
    }
}
