package yongfa365.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import yongfa365.common.Helper;
import yongfa365.config.CaffeineConfig;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyServiceTest {
    @Autowired
    MyService service;

    @Test
    public void getData() {
        log.info("开始测试缓存:{}", CaffeineConfig.Settings.Cache5Sec);
        var pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleWithFixedDelay(() -> {
            log.info("getData {}", service.getData());
        }, 0, 1, TimeUnit.SECONDS);
        Helper.sleep(15_000);
        pool.shutdown();

    }

    @Test
    public void getData2() {
        log.info("开始测试缓存:{}", CaffeineConfig.Settings.Default);

        var pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleAtFixedRate(() -> {
            log.info("getData2 {}", service.getData2());
        }, 0, 1, TimeUnit.SECONDS);
        Helper.sleep(22_000);
        pool.shutdown();

    }
}