package yongfa365.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yongfa365.common.Helper;
import yongfa365.config.CaffeineConfig;
import yongfa365.config.CaffeineLoadingCache;

import java.time.LocalDateTime;

@Slf4j
@Service
public class MyService {

    @CaffeineLoadingCache(name = CaffeineConfig.Settings.RefreshPer5Second)
    public String getDataWithCaffeineLoadingCache(String input) {
        log.info("穿透getDataWithCaffeineLoadingCache {} Start", input);
        Helper.sleep(5000);
        log.info("穿透getDataWithCaffeineLoadingCache {} End", input);
        return String.format("input:%s , data:%s", input, LocalDateTime.now().getSecond());
    }

    @Cacheable(cacheNames = CaffeineConfig.Settings.AfterWrite5Second, sync = true)
    public String getDataWithCacheable(String input) {
        log.info("穿透getDataWithCacheable {} Start", input);
        Helper.sleep(5000);
        log.info("穿透getDataWithCacheable {} End", input);
        return String.format("input:%s , data:%s", input, LocalDateTime.now().getSecond());
    }


    public String getData2(String input) {
        log.info("穿透getData2 {} Start", input);
        Helper.sleep(5000);
        log.info("穿透getData2 {} End", input);
        return String.format("input:%s , data:%s", input, LocalDateTime.now().getSecond());
    }

}
