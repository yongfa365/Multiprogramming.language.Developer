package yongfa365.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yongfa365.common.Helper;
import yongfa365.config.CaffeineConfig;
import yongfa365.config.CaffeineLoadingCache;

import java.time.LocalDateTime;

@Slf4j
@Service
public class MyService {

    @CaffeineLoadingCache(name = CaffeineConfig.Settings.RefreshPer5Second)
    public String getData(String input) {
        log.info("穿透getData {} Start", input);
        Helper.sleep(5000);
        log.info("穿透getData {} End", input);
        return String.format("input:%s , data:%s", input, LocalDateTime.now().getSecond());
    }


    public String getData2(String input) {
        log.info("穿透getData2 {} Start", input);
        Helper.sleep(5000);
        log.info("穿透getData2 {} End", input);
        return String.format("input:%s , data:%s", input, LocalDateTime.now().getSecond());
    }

}
