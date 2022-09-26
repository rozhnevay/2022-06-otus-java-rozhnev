package ru.otus.cachehw;

import org.ehcache.Cache;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) {
        new HWCacheDemo().demo();
    }

    private void demo() {
        HwCache<String, Integer> cache = new MyCache<>(initEhcache());

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        HwListener<String, Integer> listener = new HwListener<String, Integer>() {
            @Override
            public void notify(String key, Integer value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        cache.addListener(listener);
        cache.put("1", 1);

        logger.info("getValue:{}", cache.get("1"));
        cache.remove("1");
        cache.removeListener(listener);
    }

    private Cache initEhcache() {
        var cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);


        return cacheManager.createCache("Cache",
            CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Integer.class,
                ResourcePoolsBuilder.heap(5))
                .build());
    }
}
