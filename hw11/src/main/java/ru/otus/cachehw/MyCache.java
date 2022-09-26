package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private Map<K, V> cache = new WeakHashMap<>();
    private List<HwListener> listenerList = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        listenerList.forEach(listener -> listener.notify(key, value, EventType.PUT.name()));
    }

    @Override
    public void remove(K key) {
        var value = get(key);
        cache.remove(key);
        listenerList.forEach(listener -> listener.notify(key, value, EventType.REMOVED.name()));
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerList.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listenerList.remove(listener);
    }
}
