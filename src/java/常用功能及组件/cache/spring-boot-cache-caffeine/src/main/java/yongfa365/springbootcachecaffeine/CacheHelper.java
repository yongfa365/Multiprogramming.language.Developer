package yongfa365.springbootcachecaffeine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class CacheHelper {

    //默认sync=false，也就是可以并发穿透
    @Cacheable(value = "LocalCache_InMethod")
    public Integer 并发穿透测试() {
        log.info("穿透，进入方法001");
        sleep(5_000);
        return LocalDateTime.now().getSecond();
    }

    //Cacheable的方式缓存过期后会因穿透而变慢，需要设置sync防止并发请求同时穿透
    @Cacheable(value = "LocalCache_InMethod", sync = true)
    public Integer 并发穿透测试_解决() {
        log.info("穿透，进入方法002");
        sleep(5_000);
        return LocalDateTime.now().getSecond();
    }


    @Cacheable(value = "LocalCache_InMethod", sync = true)
    public Integer 首次访问或缓存过期后会穿透() {
        log.info("穿透，进入方法003");
        sleep(5_000);
        return LocalDateTime.now().getSecond();
    }

    @CacheEvict(value = "LocalCache_InMethod")
    public void 清除所有同名称的缓存() {
    }


    //    @Bean(name = "loadingCache1")
    //    public LoadingCache<String, Object> loadingCache1() {
    //        LoadingCache<String, Object> loadingCache1 = Caffeine.newBuilder()
    //                .maximumSize(150)
    //                .refreshAfterWrite(5, TimeUnit.SECONDS)
    //                .build(test -> LocalDateTime.now().toString());
    //        return loadingCache1;
    //    }

    //    public static void main(String[] args) {
    //
    //        //        LoadingCache<String, Object> loadingCache1 =
    //        //                Caffeine.newBuilder()
    //        //                        .maximumSize(150)
    //        //                        .refreshAfterWrite(5, TimeUnit.MINUTES)
    //        //                        .build(test -> "");
    //        //
    //        //        LoadingCache<String, Optional<Edition>> loadingCache2 =
    //        //                Caffeine.newBuilder()
    //        //                        .maximumSize(150)
    //        //                        .refreshAfterWrite(5, TimeUnit.MINUTES)
    //        //                        .build(test2 -> this.testRepo.find2(test2));
    //        //
    //        //        SimpleCacheManager cacheManager = new SimpleCacheManager();
    //        //        cacheManager.setCaches(List.of(
    //        //                new CaffeineCache("first", loadingCache1),
    //        //                new CaffeineCache("second", loadingCache2)));
    //
    //        new LoadingCache<Object, Object>()
    //    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
