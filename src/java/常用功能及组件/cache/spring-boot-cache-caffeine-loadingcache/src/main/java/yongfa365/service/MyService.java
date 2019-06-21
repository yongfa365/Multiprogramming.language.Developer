package yongfa365.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yongfa365.common.Helper;
import yongfa365.config.CaffeineConfig;
import yongfa365.config.SimpleCaffeineCache;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class MyService {

    @SimpleCaffeineCache(name = "now", refreshAfterWrite = 10)
    public String getData(String input)  {
        log.info("穿透getData "+input);
        Helper.sleep(5000);
        return String.format("input:%s , data:%s", input, UUID.randomUUID().hashCode());
    }


    public String getData2(String input)  {
        log.info("穿透getData2 "+input);
        Helper.sleep(5000);
        return String.format("input:%s , data:%s", input, UUID.randomUUID().hashCode());
    }

}
