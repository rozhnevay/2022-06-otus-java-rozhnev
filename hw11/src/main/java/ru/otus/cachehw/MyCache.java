package ru.otus.cachehw;


import java.util.HashMap;
import java.util.Map;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.ehcache.event.EventFiring;
import org.ehcache.event.EventOrdering;
import org.ehcache.event.EventType;

public class MyCache<K, V> implements HwCache<K, V> {
    private Cache<K, V> cache;
    private Map<HwListener, CacheEventListener> listenerMap = new HashMap<>();

    public MyCache(Cache<K, V> cache) {
        this.cache = cache;
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        CacheEventListener<K, V> cacheEventListener = new CacheEventListener() {
            @Override
            public void onEvent(CacheEvent event) {
                listener.notify((K) event.getKey(), (V) event.getNewValue(), event.getType().name());
            }
        };

        listenerMap.put(listener, cacheEventListener);

        cache.getRuntimeConfiguration().registerCacheEventListener(cacheEventListener, EventOrdering.ORDERED, EventFiring.ASYNCHRONOUS, EventType.CREATED, EventType.UPDATED);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        var cacheEventListener = listenerMap.get(listener);
        if (cacheEventListener == null) {
            return;
        }

        cache.getRuntimeConfiguration().deregisterCacheEventListener(cacheEventListener);
    }
}
