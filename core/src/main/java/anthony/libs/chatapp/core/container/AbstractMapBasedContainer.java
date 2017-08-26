package anthony.libs.chatapp.core.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by chend on 2017/8/10.
 */
public abstract class AbstractMapBasedContainer<K, V> {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Map<K, V> mapContainer = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Logger getLogger() {
        return logger;
    }

    public V get(K k) {
        V v;
        lock.readLock().lock();
        v = mapContainer.get(k);
        lock.readLock().unlock();
        return v;
    }

    public ArrayList<V> getAll() {
        ArrayList<V> allList = new ArrayList<>();
        lock.readLock().lock();
        mapContainer.forEach((k, v) -> allList.add(v));
        lock.readLock().unlock();
        return allList;
    }

    public void put(K k, V v) {
        lock.writeLock().lock();
        mapContainer.put(k, v);
        lock.writeLock().unlock();
    }

    public void remove(K k) {
        lock.writeLock().lock();
        mapContainer.remove(k);
        lock.writeLock().unlock();
    }

    public boolean containsKey(K k) {
        return mapContainer.containsKey(k);
    }

    public int size() {
        return mapContainer.size();
    }
}
