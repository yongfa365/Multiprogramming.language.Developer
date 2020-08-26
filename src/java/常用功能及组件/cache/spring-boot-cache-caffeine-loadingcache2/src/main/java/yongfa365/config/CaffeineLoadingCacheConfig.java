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

    private static final ConcurrentHashMap<String, LoadingCache<CacheKey, Object>> LOADING_CACHES = new ConcurrentHashMap<>();
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
                    builder.removalListener((key, value, cause) -> log.info("Key:{} was removed ({})", key, cause));
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

                    LoadingCache<CacheKey, Object> loadingCache = builder.build(key -> {
                        try {
                            //log.info("000000000000，proceed,观察是否多次进入");
                            return key.getJoinPoint().proceed();
                        } catch (Throwable throwable) {
                            Thread.sleep(config.timeout() * 1000);
                            var message = "RefreshCacheException：" + throwable.getMessage() + "\n 【CacheKey：" + key + "】\n【JoinPoint： " + key.getJoinPoint().getSignature() + "】";
                            //需要设置捕捉线程异常才能收集到此日志
                            throw new RuntimeException(message, throwable);
                        }
                    });

                    LOADING_CACHES.put(cacheName, loadingCache);
                }
            }
        }

        //TODO:默认就是同时只有一个穿透，那@cacheable(sync=false)是怎么做到的？
        var cache = LOADING_CACHES.get(cacheName);
        var key = new CacheKey(joinPoint);
        return cache.get(key);
    }

    private static class CacheKey implements Serializable {
        private final int hashCode;
        private final String signature;
        private final ProceedingJoinPoint joinPoint;

        CacheKey(ProceedingJoinPoint joinPoint) {
            this.joinPoint = joinPoint;
            this.signature = joinPoint.getSignature().toLongString();
            this.hashCode = Arrays.deepHashCode(joinPoint.getArgs()) + signature.hashCode();
        }

        ProceedingJoinPoint getJoinPoint() {
            return joinPoint;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj instanceof CacheKey) {
                var item = (CacheKey) obj;
                return this.signature.equals(item.signature);
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
            for (Object param : joinPoint.getArgs()) {
                joiner.add(param.toString());
            }
            return getClass().getSimpleName() + ":" + signature + ":" + joiner.toString();
        }

    }

}
