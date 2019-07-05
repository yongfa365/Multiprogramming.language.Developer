package yongfa365.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineConfig {

    /**
     * 自定义CacheManager实现多种缓存过期策略
     */
    @Bean
    public CacheManager cacheManager() {
        var caches = new ArrayList<CaffeineCache>();

        var fields = CaffeineConfig.Settings.class.getDeclaredFields();
        for (var field : fields) {
            var annotation = field.getAnnotation(CacheConfigInfo.class);

            var builder = Caffeine.newBuilder();
            if (annotation.recordStats()) {
                builder.recordStats();
            }
            if (annotation.expireAfterWriteSeconds() != -1) {
                builder.expireAfterWrite(annotation.expireAfterWriteSeconds(), TimeUnit.SECONDS);
            }
            if (annotation.expireAfterAccessSeconds() != -1) {
                builder.expireAfterAccess(annotation.expireAfterAccessSeconds(), TimeUnit.SECONDS);
            }
            if (annotation.maximumSize() != -1) {
                builder.maximumSize(annotation.maximumSize());
            }

            var name = "";
            try {
                name = field.get(null).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            var cache = new CaffeineCache(name, builder.build());
            caches.add(cache);

        }

        var manager = new SimpleCacheManager();
        manager.setCaches(caches);
        return manager;
    }

    /**
     * 缓存配置，使用方法：@Cacheable(cacheNames = CaffeineConfig.Settings.Cache5Sec)
     * 有需要时就在这里添加相关字段并自定义注解便可
     */
    public static final class Settings {
        @CacheConfigInfo(expireAfterWriteSeconds = 5)
        public static final String Cache5Sec = "Cache5Sec";

        @CacheConfigInfo(expireAfterWriteSeconds = 10)
        public static final String Default = "Default";

        @CacheConfigInfo(expireAfterWriteSeconds = 5 * 60)
        public static final String Cache5Min = "Cache5Min";

        @CacheConfigInfo(expireAfterWriteSeconds = 15 * 60)
        public static final String Cache15Min = "Cache15Min";

        @CacheConfigInfo(expireAfterWriteSeconds = 60 * 60)
        public static final String Cache60Min = "Cache60Min";
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CacheConfigInfo {
        long maximumSize() default -1;

        long expireAfterWriteSeconds() default -1;

        long expireAfterAccessSeconds() default -1;

        //long refreshAfterWriteSeconds() default -1;

        boolean recordStats() default true;
    }

}
