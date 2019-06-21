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
        pool.scheduleWithFixedDelay(() -> {
            log.info("getData2 {}", service.getData2());
        }, 0, 1, TimeUnit.SECONDS);
        Helper.sleep(22_000);
        pool.shutdown();

    }

    @Test
    public void getData3() {
        log.info("开始测试不同参数返回不同缓存，不会覆盖");

        var pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleWithFixedDelay(() -> {
            log.info("getData3 ,input task111, 执行结果： {}", service.getData3("task111"));
        }, 0, 1, TimeUnit.SECONDS);


        var pool2 = Executors.newSingleThreadScheduledExecutor();
        pool2.scheduleWithFixedDelay(() -> {
            log.info("getData3 ,input task222, 执行结果： {}", service.getData3("task222"));
        }, 0, 1, TimeUnit.SECONDS);

        Helper.sleep(22_000);

        pool.shutdown();
        //pool2.shutdown();

    }
}