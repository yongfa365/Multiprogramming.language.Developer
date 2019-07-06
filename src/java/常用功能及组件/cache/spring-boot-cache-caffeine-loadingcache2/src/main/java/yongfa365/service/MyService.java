package yongfa365.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yongfa365.common.Helper;
import yongfa365.config.CacheablePlus;

import java.time.LocalDateTime;

@Slf4j
@Service
public class MyService {

    @CacheablePlus(name = "可以不要", expireAfterWrite = 20, refreshAfterWrite = 5, maximumSize = 1000, recordStats = true)
    String getDataWithCaffeineLoadingCache(String input) {
        log.info("穿透getDataWithCaffeineLoadingCache {} Start", input);
        Helper.sleep(5000);
        log.info("穿透getDataWithCaffeineLoadingCache {} End", input);
        return String.format("input:%s , data:%s", input, LocalDateTime.now().getSecond());
    }

    @CacheablePlus(name = "缓存过期后等待，不刷新", expireAfterWrite = 5)
    String getDataWithCaffeineNoLoadingCache(String input) {
        log.info("穿透getDataWithCaffeineNoLoadingCache{} Start", input);
        Helper.sleep(5000);
        log.info("穿透getDataWithCaffeineNoLoadingCache {} End", input);
        return String.format("input:%s , data:%s", input, LocalDateTime.now().getSecond());
    }


    String getData2(String input) {
        log.info("穿透getData2 {} Start", input);
        Helper.sleep(5000);
        log.info("穿透getData2 {} End", input);
        return String.format("input:%s , data:%s", input, LocalDateTime.now().getSecond());
    }

}
