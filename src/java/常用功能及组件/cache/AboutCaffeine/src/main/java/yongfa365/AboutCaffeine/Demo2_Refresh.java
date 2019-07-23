package yongfa365.AboutCaffeine;


import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Demo2_Refresh {

    //★返回类型从Cache变成LoadingCache了。
    private static LoadingCache<Object, Object> LocalCache = Caffeine.newBuilder()
            .refreshAfterWrite(5, TimeUnit.SECONDS)
            //★数据填充方法，首次创建及之后的刷新 都在这里完成。
            .build(key -> {
                if ("key1".equals(key)) {
                    return "key1的值：" + LocalDateTime.now();
                } else if ("key2".equals(key)) {
                    return LocalDateTime.now();
                } else if ("模拟耗时操作.Key3".equals(key)) {
                    log.info("模拟耗时操作.Start");
                    Helper.sleep(5000);
                    var now = LocalTime.now().getSecond();
                    log.info("模拟耗时操作.End");
                    return now;
                }
                //其他Key返回null，使用刷新机制就不要用put了，不然会被刷新成null
                return null;
            });

    public static void main(String[] args) {
        //调用方法也从getIfPresent变成get了，一定会拿到值才走，会等待。
        var obj1 = LocalCache.get("key1");
        var obj2 = LocalCache.get("key2");
try {

}catch (Throwable ex)
{
    Thread.sleep(1000);
    throw new RuntimeException("RefreshCacheException", throwable);
}
        log.info("★证明：虽然同时开了10个线程，但实际是只有1个请求会穿透，其他请求会等待并拿第1次的结果来响应。");
        {
            log.info("开启10个线程.Start");
            var executorService = Executors.newFixedThreadPool(10);
            for (int i = 0; i < 10; i++) {
                executorService.submit(() -> {
                    log.info("数据:" + LocalCache.get("模拟耗时操作.Key3"));
                });
            }
            log.info("开启10个线程.End");

            Helper.sleep(10 * 1000);
            LocalCache.invalidateAll();
            log.info("\n\n===========清空所有缓存，以免影响后续测试===========\n");
        }


        log.info("★证明：首次调用会等待，之后调用直接返回旧值，同时后台更新");
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
            log.info("数据:" + LocalCache.get("模拟耗时操作.Key3"));
        }, 0, 1, TimeUnit.SECONDS);


    }
}

//输出结果：
//2019-06-13 18:19:32 INFO  ★证明：虽然同时开了10个线程，但实际是只有1个请求会穿透，其他请求会等待并拿第1次的结果来响应。
//2019-06-13 18:19:32 INFO  开启10个线程.Start
//2019-06-13 18:19:32 INFO  模拟耗时操作.Start
//2019-06-13 18:19:32 INFO  开启10个线程.End
//2019-06-13 18:19:37 INFO  模拟耗时操作.End
//2019-06-13 18:19:37 INFO  数据:37
//2019-06-13 18:19:37 INFO  数据:37
//2019-06-13 18:19:37 INFO  数据:37
//2019-06-13 18:19:37 INFO  数据:37
//2019-06-13 18:19:37 INFO  数据:37
//2019-06-13 18:19:37 INFO  数据:37
//2019-06-13 18:19:37 INFO  数据:37
//2019-06-13 18:19:37 INFO  数据:37
//2019-06-13 18:19:37 INFO  数据:37
//2019-06-13 18:19:37 INFO  数据:37
//2019-06-13 18:19:42 INFO
//
// ===========清空所有缓存，以免影响后续测试===========
//
//2019-06-13 18:19:42 INFO  ★证明：首次调用会等待，之后调用直接返回旧值，同时后台更新
//2019-06-13 18:19:42 INFO  模拟耗时操作.Start
//2019-06-13 18:19:47 INFO  模拟耗时操作.End
//2019-06-13 18:19:47 INFO  数据:47
//2019-06-13 18:19:48 INFO  数据:47
//2019-06-13 18:19:49 INFO  数据:47
//2019-06-13 18:19:50 INFO  数据:47
//2019-06-13 18:19:51 INFO  数据:47
//2019-06-13 18:19:52 INFO  模拟耗时操作.Start
//2019-06-13 18:19:52 INFO  数据:47
//2019-06-13 18:19:53 INFO  数据:47
//2019-06-13 18:19:54 INFO  数据:47
//2019-06-13 18:19:55 INFO  数据:47
//2019-06-13 18:19:56 INFO  数据:47
//2019-06-13 18:19:57 INFO  模拟耗时操作.End
//2019-06-13 18:19:57 INFO  数据:47
//2019-06-13 18:19:58 INFO  数据:57
//2019-06-13 18:19:59 INFO  数据:57
//2019-06-13 18:20:00 INFO  数据:57