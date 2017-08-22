package anthony.libs.chatapp.core.container;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by chend on 2017/8/22.
 */
public class FutureList<V> extends AbstractBlockingQueueBasedContainer<Future<V>> {

    @Override
    public Future<V> take() {
        Future<V> v;
        try {
            v = super.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            v = null;
        }
        return v;
    }

    @Override
    public void put(Future<V> v) {
        try {
            super.put(v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Future<V> getDoneFuture() {
        Future<V> future = null;
        while (future == null) {
            future = take();
            if (!future.isDone()) {
                put(future);
                future = null;
            }
        }
        return future;
    }

    public V getElement() {
        V v = null;
        try {
            Future<V> future = getDoneFuture();
            if (future != null)
                v = future.get();
        } catch (InterruptedException | ExecutionException e) {
            v = null;
            e.printStackTrace();
        }
        return v;
    }

}
