package com.anthony.chatapp.core.newcore.container;

/**
 * Created by chend on 2017/7/12.
 */
public interface Container<V> {
    public void add(V v);

    public V remove(V v);

    public V removeById(String id);

    public V getById(String id);

}
