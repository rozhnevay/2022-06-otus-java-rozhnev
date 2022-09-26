package ru.otus.cachehw;


import org.ehcache.event.CacheEventListener;

public interface HwListener<K, V> {
    void notify(K key, V value, String action);
}
