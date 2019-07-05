package yongfa365.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yongfa365.common.Helper;
import yongfa365.config.CaffeineConfig;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class MyService {
    /**
     * 缓存5s
     */
    @Cacheable(cacheNames = CaffeineConfig.Settings.Cache5Sec, sync = true)
    public int getData() {
        log.info("穿透getData");
        Helper.sleep(3000);
        return LocalDateTime.now().getSecond();
    }

    @Cacheable(cacheNames = CaffeineConfig.Settings.Default, sync = true)
    public int getData2() {
        log.info("穿透getData2");
        Helper.sleep(3000);
        return LocalDateTime.now().getSecond();
    }

    /**
     * 根据参数缓存
     */
    @Cacheable(cacheNames = CaffeineConfig.Settings.Cache5Sec, sync = true)
    public String getData3(String input) {
        log.info("穿透getData3 " + input);
        Helper.sleep(3000);
        return String.format("input:%s , data:%s", input, UUID.randomUUID().hashCode());
    }


    /**
     * 随意写个cacheName是会报错的
     */
    @Cacheable(cacheNames = "xxxxxxxxxxxxxxxxxx", sync = true)
    public String getData4(String input) {
        return String.format("input:%s , data:%s", input, UUID.randomUUID().hashCode());
    }
}
