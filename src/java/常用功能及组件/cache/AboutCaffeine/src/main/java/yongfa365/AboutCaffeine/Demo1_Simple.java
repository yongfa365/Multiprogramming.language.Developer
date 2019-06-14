package yongfa365.AboutCaffeine;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Demo1_Simple {

    //可以定义多个缓存变量，每个里又可以放多个缓存
    private static Cache<Object, Object> LocalCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .removalListener((key, value, cause) -> log.info("Key:{} was removed ({})", key, cause))
            .recordStats() //记录数字如：hit,miss,loadFailure,loadCount等
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            //使用默认机制，添加或更新缓存全靠自己再调用其他方法完成。
            .build();

    public static void main(String[] args) {
        // Lookup an entry, or null if not found
        var obj1 = LocalCache.getIfPresent("key1");

        // Insert or update an entry
        LocalCache.put("key1", "value1");
        LocalCache.put("key1", "value2");
        LocalCache.put("key2", LocalDateTime.now());
        LocalCache.put("key3", List.of(1, 2, 3, 4, 5));

        // Lookup an entry, or null if not found
        var obj3 = (List) LocalCache.getIfPresent("key3");

        //方法名说是估算，简单测试貌似就是真实的数量
        var size = LocalCache.estimatedSize();

        // Remove an entry or entries or all entries
        LocalCache.invalidate("key2");
        LocalCache.invalidateAll(List.of("key1", "key2", "key5"));
        LocalCache.invalidateAll();

        log.info(LocalCache.stats().toString());
    }
}

//输出结果：
//2019-06-13 18:37:17INFO Key:key1 was removed(REPLACED)
//2019-06-13 18:37:17INFO Key:key2 was removed(EXPLICIT)
//2019-06-13 18:37:17INFO Key:key1 was removed(EXPLICIT)
//2019-06-13 18:37:17INFO Key:key3 was removed(EXPLICIT)
//2019-06-13 18:37:17INFO CacheStats{hitCount=1,missCount=1,loadSuccessCount=0,loadFailureCount=0,totalLoadTime=0,evictionCount=0,evictionWeight=0}