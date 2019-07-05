package yongfa365.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Configuration
@EnableCaching //aop的不需要这个，是给CacheManager这个Bean用的
public class CaffeineConfig {

    private static final ConcurrentHashMap<String, LoadingCache> LOADING_CACHES = new ConcurrentHashMap<>();
    private static final Object LOCK = new Object();

    @Around("@annotation(nowAnnotation)")
    public Object autoRefreshCache(ProceedingJoinPoint joinPoint, CaffeineLoadingCache nowAnnotation) {
        // var annotation = joinPoint.getSignature().getClass().getAnnotation(CaffeineLoadingCache.class);
        var cacheName = nowAnnotation.name();

        //double check lock TODO:防止并发添加到HashMap锁引起的并发穿透？
        if (!LOADING_CACHES.containsKey(cacheName)) {
            synchronized (LOCK) {
                if (!LOADING_CACHES.containsKey(cacheName)) {
                    initCache(joinPoint, cacheName);
                }
            }
        }

        //TODO:默认就是同时只有一个穿透，那@cacheable(sync=false)是怎么做到的？
        var cache = LOADING_CACHES.get(cacheName);
        var key = new CacheKey(joinPoint.getSignature().toLongString(), joinPoint.getArgs());
        return cache.get(key);
    }

    private void initCache(ProceedingJoinPoint joinPoint, String cacheName) {
        Field field;
        try {
            field = Settings.class.getDeclaredField(cacheName);
        } catch (NoSuchFieldException e) {
            //TODO:这个错调用方看不到
            throw new RuntimeException("name请使用CaffeineConfig.Settings里的", e);
        }

        var builder = getCaffeineBuilder(field, true);
        var loadingCache = builder.build(key -> {
            try {
                //log.debug("000000000000，proceed,观察是否多次进入");
                return joinPoint.proceed(((CacheKey) key).getParams());
            } catch (Throwable throwable) {
                throw new RuntimeException("RefreshCacheException", throwable);
            }
        });

        LOADING_CACHES.put(cacheName, loadingCache);
    }


    /**
     * 自定义CacheManager实现多种缓存过期策略，不支持LoadingCache
     */
    @Bean
    public CacheManager cacheManager() {
        var caches = new ArrayList<CaffeineCache>();

        var fields = CaffeineConfig.Settings.class.getDeclaredFields();
        for (var field : fields) {
            var builder = getCaffeineBuilder(field, false);

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

    private Caffeine<Object, Object> getCaffeineBuilder(Field field, boolean isCanRefresh) {
        var builder = Caffeine.newBuilder();
        var annotation = field.getAnnotation(CacheConfigInfo.class);
        if (annotation.recordStats()) {
            builder.recordStats();
        }
        if (annotation.expireAfterWriteSeconds() != -1) {
            builder.expireAfterWrite(annotation.expireAfterWriteSeconds(), TimeUnit.SECONDS);
        }
        if (annotation.expireAfterAccessSeconds() != -1) {
            builder.expireAfterAccess(annotation.expireAfterAccessSeconds(), TimeUnit.SECONDS);
        }
        if (annotation.refreshAfterWriteSeconds() != -1 && isCanRefresh) {
            builder.refreshAfterWrite(annotation.refreshAfterWriteSeconds(), TimeUnit.SECONDS);
        }
        if (annotation.maximumSize() != -1) {
            builder.maximumSize(annotation.maximumSize());
        }
        return builder;
    }

    /**
     * 缓存配置，使用方法：@Cacheable(cacheNames = CaffeineConfig.Settings.Cache5Sec)
     * 有需要时就在这里添加相关字段并自定义注解便可
     */
    public static final class Settings {
        @CacheConfigInfo(refreshAfterWriteSeconds = 5)
        public static final String RefreshPer5Second = "RefreshPer5Second";

        @CacheConfigInfo(expireAfterWriteSeconds = 5 * 60, refreshAfterWriteSeconds = 5)
        public static final String RefreshDefault = "RefreshDefault";

        @CacheConfigInfo(expireAfterWriteSeconds = 5)
        public static final String AfterWrite5Second = "AfterWrite5Second";

    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface CacheConfigInfo {
        long maximumSize() default -1;

        long expireAfterWriteSeconds() default -1;

        long expireAfterAccessSeconds() default -1;

        long refreshAfterWriteSeconds() default -1;

        boolean recordStats() default true;
    }

    private static class CacheKey implements Serializable {
        private final Object[] params;
        private final int hashCode;
        private final String signature;

        CacheKey(String signature, Object... elements) {
            this.signature = signature;
            this.params = new Object[elements.length];
            System.arraycopy(elements, 0, this.params, 0, elements.length);
            this.hashCode = Arrays.deepHashCode(this.params) + signature.hashCode(); //非专业处理
        }

        Object[] getParams() {
            return this.params;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj instanceof CacheKey) {
                var item = (CacheKey) obj;
                return this.signature.equals(item.signature) && Arrays.deepEquals(this.params, item.params);
            }
            return false;
        }

        @Override
        public final int hashCode() {
            return hashCode;
        }

        @Override
        public String toString() {
            var joiner = new StringJoiner(",", "[", "]");
            for (Object param : this.params) {
                joiner.add(param.toString());
            }
            return getClass().getSimpleName() + ":" + signature + ":" + joiner.toString();
        }

    }

}
