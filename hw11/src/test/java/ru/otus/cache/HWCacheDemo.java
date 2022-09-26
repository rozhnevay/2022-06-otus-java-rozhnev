package ru.otus.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) {
        new HWCacheDemo().demo();
    }

    private void demo() {
        ru.otus.cachehw.HwCache<String, Integer> cache = new ru.otus.cachehw.MyCache<>();

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        ru.otus.cachehw.HwListener<String, Integer> listener = new ru.otus.cachehw.HwListener<String, Integer>() {
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
}
