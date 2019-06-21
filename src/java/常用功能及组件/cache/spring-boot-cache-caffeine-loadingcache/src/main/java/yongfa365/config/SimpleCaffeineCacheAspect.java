package yongfa365.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component

public class SimpleCaffeineCacheAspect {
    //    @Bean 加这个会导致执行了2次
    //    public SimpleCaffeineCacheAspect simpleCaffeineCache() {
    //        return new SimpleCaffeineCacheAspect();
    //    }

    ConcurrentHashMap<String, LoadingCache> caches = new ConcurrentHashMap<>();


    @Around("@annotation(annotation)")
    public Object autoRefreshCache(ProceedingJoinPoint joinPoint, SimpleCaffeineCache annotation) {

        // var annotation = joinPoint.getSignature().getClass().getAnnotation(SimpleCaffeineCache.class);

        if (!caches.containsKey(annotation.name())) {
            var builder = Caffeine.newBuilder();
            if (annotation.maximumSize() != -1) {
                builder.maximumSize(annotation.maximumSize());
            }
            if (annotation.expireAfterWrite() != -1) {
                builder.expireAfterWrite(annotation.expireAfterWrite(), TimeUnit.SECONDS);
            }
            if (annotation.expireAfterAccess() != -1) {
                builder.expireAfterAccess(annotation.expireAfterAccess(), TimeUnit.SECONDS);
            }
            if (annotation.refreshAfterWrite() != -1) {
                builder.refreshAfterWrite(annotation.refreshAfterWrite(), TimeUnit.SECONDS);
            }

            caches.put(annotation.name(), builder.build(key -> {
                try {
                    //log.debug("000000000000，proceed,观察是否多次进入");
                    return joinPoint.proceed(((CacheKey) key).getParams());

                } catch (Throwable throwable) {
                    throw new RuntimeException("RefreshCacheException", throwable);
                }


            }));
        }


        var cache = caches.get(annotation.name());

        var key = new CacheKey(joinPoint.getArgs());
        var result = cache.get(key);
        return result;
    }

    private static class CacheKey implements Serializable {

        public static final CacheKey EMPTY = new CacheKey();

        private final Object[] params;
        private final int hashCode;


        CacheKey(Object... elements) {

            this.params = new Object[elements.length];
            System.arraycopy(elements, 0, this.params, 0, elements.length);
            this.hashCode = Arrays.deepHashCode(this.params);

        }

        Object[] getParams() {
            return this.params;
        }

        @Override
        public boolean equals(Object obj) {
            return (this == obj || (obj instanceof CacheKey && Arrays.deepEquals(this.params, ((CacheKey) obj).params)));
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
            return getClass().getSimpleName() + joiner.toString();
        }

    }
}