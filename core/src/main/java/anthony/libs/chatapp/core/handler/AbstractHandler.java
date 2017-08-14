package anthony.libs.chatapp.core.handler;

import java.util.concurrent.Callable;

/**
 * Created by chend on 2017/8/14.
 */
public abstract class AbstractHandler<V> implements Callable<V> {
    public abstract V handle();

    @Override
    public V call() {
        return handle();
    }
}
