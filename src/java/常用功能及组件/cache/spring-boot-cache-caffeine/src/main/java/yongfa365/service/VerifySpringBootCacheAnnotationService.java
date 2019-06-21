package yongfa365.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import yongfa365.common.Helper;

import java.time.LocalDateTime;

@Slf4j
@Component
public class VerifySpringBootCacheAnnotationService {

    //默认sync=false，也就是可以并发穿透
    @Cacheable(value = "LocalCache_OnMethod")
    public Integer 并发穿透() {
        log.info("穿透，进入方法001");
        Helper.sleep(5_000);
        return LocalDateTime.now().getSecond();
    }


    //Cacheable的方式缓存过期后会因穿透而变慢，需要设置sync防止并发请求同时穿透
    @Cacheable(value = "LocalCache_OnMethod", sync = true)
    public Integer 并发仅一个穿透() {
        log.info("穿透，进入方法002");
        Helper.sleep(5_000);
        return LocalDateTime.now().getSecond();
    }


    @Cacheable(value = "LocalCache_OnMethod", sync = true)
    public Integer 首次访问或缓存过期后会穿透() {
        log.info("穿透，进入方法003");
        Helper.sleep(5_000);
        return LocalDateTime.now().getSecond();
    }



    @Cacheable(value = "LocalCache_OnMethod",sync = true)
    public Integer 带参数测试(String input) {
        log.info("穿透，进入方法 输入：{}",input);
        Helper.sleep(3_000);
        return LocalDateTime.now().getSecond();
    }

    @Cacheable(value = "LocalCache_OnMethod",sync = true,key = "111111111")
    public String 带参数带固定key测试(String input) {
        log.info("穿透getData {} Start", input);
        Helper.sleep(5000);
        log.info("穿透getData {} End", input);
        return String.format("input:%s , data:%s", input, LocalDateTime.now().getSecond());
    }

    @CacheEvict(value = "LocalCache_OnMethod", allEntries = true)
    public void 清除所有同名称的缓存() {
    }


}
