package yongfa365.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Configuration
public class CaffeineLoadingCacheConfig {

    private static final ConcurrentHashMap<String, LoadingCache> LOADING_CACHES = new ConcurrentHashMap<>();
    private static final Object LOCK = new Object();

    @Around("@annotation(config)")
    public Object autoRefreshCache(ProceedingJoinPoint joinPoint, CacheableLoading config) {
        var cacheName = String.format("%s:%s:%s:%s:%s:%s:%s", config.name(), config.maximumSize(),
                config.expireAfterWrite(), config.expireAfterAccess(), config.refreshAfterWrite(),
                config.recordStats(), config.timeout());

        //double check lock TODO:防止并发添加到HashMap锁引起的并发穿透？
        if (!LOADING_CACHES.containsKey(cacheName)) {
            synchronized (LOCK) {
                if (!LOADING_CACHES.containsKey(cacheName)) {
                    var builder = Caffeine.newBuilder();

                    if (config.recordStats()) {
                        builder.recordStats();
                    }
                    if (config.expireAfterWrite() != -1) {
                        builder.expireAfterWrite(config.expireAfterWrite(), TimeUnit.SECONDS);
                    }
                    if (config.expireAfterAccess() != -1) {
                        builder.expireAfterAccess(config.expireAfterAccess(), TimeUnit.SECONDS);
                    }
                    if (config.refreshAfterWrite() != -1) {
                        builder.refreshAfterWrite(config.refreshAfterWrite(), TimeUnit.SECONDS);
                    }
                    if (config.maximumSize() != -1) {
                        builder.maximumSize(config.maximumSize());
                    }

                    var loadingCache = builder.build(key -> {
                        try {
                            //log.info("000000000000，proceed,观察是否多次进入");
                            return joinPoint.proceed(((CacheKey) key).getParams());
                        } catch (Throwable throwable) {
                            Thread.sleep(config.timeout() * 1000);
                            throw new RuntimeException("RefreshCacheException", throwable);
                        }
                    });

                    LOADING_CACHES.put(cacheName, loadingCache);
                }
            }
        }

        //TODO:默认就是同时只有一个穿透，那@cacheable(sync=false)是怎么做到的？
        var cache = LOADING_CACHES.get(cacheName);
        var key = new CacheKey(joinPoint.getSignature().toLongString(), joinPoint.getArgs());
        return cache.get(key);
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
