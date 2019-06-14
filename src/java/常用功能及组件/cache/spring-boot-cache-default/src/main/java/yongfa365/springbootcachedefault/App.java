package yongfa365.springbootcachedefault;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
@EnableCaching //记得加这个
public class App implements ApplicationRunner {

    @Autowired
    CacheHelper cacheHelper;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
            log.info("输出:" + cacheHelper.getData(1));
        }, 0, 1, TimeUnit.SECONDS);
    }
}
