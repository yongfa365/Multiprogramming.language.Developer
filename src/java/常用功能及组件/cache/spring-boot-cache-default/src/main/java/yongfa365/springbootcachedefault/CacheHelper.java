package yongfa365.springbootcachedefault;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CacheHelper {

    //默认的，没有配置缓存组件的，就是缓存下来不过期,内部使用的是ConcurrentHashMap
    @Cacheable("LocalCache_001")
    public int getData(int i) {
        return LocalDateTime.now().getSecond();
    }

}
