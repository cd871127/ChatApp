package anthony.libs.chatapp.core.service;

import java.util.concurrent.Callable;

/**
 * Created by chend on 2017/8/10.
 */
public abstract class AbstractCallableService<V> extends AbstractService implements Callable<V>{
    @Override
    protected final void execute()
    {
        try {
            call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
