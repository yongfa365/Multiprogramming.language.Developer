package yongfa365.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yongfa365.common.Helper;
import yongfa365.config.CaffeineConfig;

import java.time.LocalDateTime;

@Slf4j
@Service
public class MyService {
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
}
