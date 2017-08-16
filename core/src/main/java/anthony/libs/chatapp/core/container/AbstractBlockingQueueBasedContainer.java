package anthony.libs.chatapp.core.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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

    protected 
}
