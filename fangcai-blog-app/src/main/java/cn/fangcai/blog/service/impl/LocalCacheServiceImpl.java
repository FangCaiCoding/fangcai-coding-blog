package cn.fangcai.blog.service.impl;

import cn.fangcai.blog.service.ICacheService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author MouFangCai
 * @date 2024/10/26 20:17
 * @description
 */
@Service
public class LocalCacheServiceImpl implements ICacheService {


    private final Cache<String, LocalCacheObj<String>> cache;


    static class LocalCacheObj<String> {
        private final String value;
        private final long duration;

        public LocalCacheObj(String value, long duration) {
            this.value = value;
            this.duration = duration;
        }
    }

    public LocalCacheServiceImpl() {
        // 初始化缓存配置，可以根据需要自定义
        this.cache = Caffeine.newBuilder()
                .maximumSize(3000)                      // 缓存最大数量
                .expireAfter(new Expiry<String, LocalCacheObj<String>>() {

                    @Override
                    public long expireAfterCreate(String key, LocalCacheObj<String> value, long currentTime) {
                        return TimeUnit.SECONDS.toNanos(value.duration);
                    }

                    @Override
                    public long expireAfterUpdate(String key, LocalCacheObj<String> value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }

                    @Override
                    public long expireAfterRead(String key, LocalCacheObj<String> value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }
                })
                .build();
    }

    /**
     * 从缓存获取数据
     *
     * @param key 缓存键
     *
     * @return 缓存值，若不存在则返回 null
     */
    @Override
    public String get(String key) {
        return cache.getIfPresent(key) == null ? null : Objects.requireNonNull(cache.getIfPresent(key)).value;
    }


    /**
     * 从缓存获取数据
     *
     * @param key 缓存键
     *
     * @return 缓存值，若不存在则返回 null
     */
    @Override
    public String getAndDel(String key) {
        String value = cache.getIfPresent(key) == null ? null : Objects.requireNonNull(cache.getIfPresent(key)).value;
        this.del(key);
        return value;
    }


    /**
     * 向缓存添加数据
     *
     * @param key      缓存键
     * @param value    缓存值
     * @param duration 有效期，单位：秒
     */
    @Override
    public void put(String key, String value, long duration) {
        cache.put(key, new LocalCacheObj<>(value, duration));
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    private void del(String key) {
        cache.invalidate(key);
    }


    public static void main(String[] args) throws InterruptedException {
        LocalCacheServiceImpl cacheService = new LocalCacheServiceImpl();
        cacheService.put("key1", "value1", 3);
        cacheService.put("key2", "value2", 5);
        cacheService.put("key3", "value3", 10);

        Thread.sleep(1000);
        System.out.println(cacheService.get("key1"));
        Thread.sleep(3000);
        System.out.println(cacheService.get("key1"));
        System.out.println(cacheService.getAndDel("key2"));
        System.out.println(cacheService.get("key2"));

        Thread.sleep(4000);
        System.out.println(cacheService.get("key1"));
        System.out.println(cacheService.get("key2"));
        System.out.println(cacheService.get("key3"));

    }
}


