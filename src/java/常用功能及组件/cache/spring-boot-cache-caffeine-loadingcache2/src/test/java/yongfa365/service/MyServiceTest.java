package yongfa365.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import yongfa365.common.Helper;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyServiceTest {
    @Autowired
    MyService service;


    @Test
    public void T1_穿透后_依然立即返回旧值_后台会刷新数据() {
        var pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleWithFixedDelay(() -> {
            log.info("input:11111 output: {}", service.getDataWithCaffeineLoadingCache("11111"));
        }, 0, 1, TimeUnit.SECONDS);
        Helper.sleep(30_000);
        pool.shutdown();
    }

    @Test
    public void T1_穿透后_依然立即返回旧值_后台会刷新数据_后台出错没影响() {
        log.info("开始正常-->随后报错(5s后后台有错（黑色的）)-->正常拿老的缓存数据-->每隔5s报一次错-->到20s时缓存过期，真的穿透了，调用方要等待");
        log.info("等待过程中还是没数据，调用方看到异常（红色的）-->最后程序正常后，一切恢复正常");
        var pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleWithFixedDelay(() -> {
            try {
                log.info("input:11111 output: {}", service.getDataWithCaffeineLoadingCacheWithException("11111"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
        Helper.sleep(40_000);
        pool.shutdown();
    }

    @Test
    public void T1_1_穿透后等待() {
        var pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleWithFixedDelay(() -> {
            log.info("input:11111 output: {}", service.getDataWithCaffeineNoLoadingCache("11111"));
        }, 0, 1, TimeUnit.SECONDS);
        Helper.sleep(30_000);
        pool.shutdown();
    }

    @Test
    public void T2_测试是否会并发穿透() {
        log.info("正常应该只能看到一个穿透，其他等有结果后直接返回");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                log.info("getData {}", service.getDataWithCaffeineLoadingCache("11111"));
            }).start();
        }
        Helper.sleep(20_000);
    }

    @Test
    public void T3_测试是否根据条件缓存() {
        log.info("同一行应该看到的input与结果一样，都是11111或22222");
        var i = 20;
        while (i-- > 0) {
            log.info("getData  input:11111,  结果： {}", service.getDataWithCaffeineLoadingCache("11111"));
            log.info("getData  input:22222,  结果： {}", service.getDataWithCaffeineLoadingCache("22222"));
            Helper.sleep(1000);
        }

        Helper.sleep(5000);
    }


    @Test
    public void 测试没有加特性的不应被缓存() {
        log.info("应该每次都要等比较久");
        var pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleWithFixedDelay(() -> {
            log.info("getData  input:11111,  结果： {}", service.getData2("11111"));

        }, 0, 1, TimeUnit.SECONDS);
        Helper.sleep(20_000);
        pool.shutdown();
    }


    @Test
    public void expireAfterAccess() throws InterruptedException {
        log.info("getData  input:11111,  结果： {}", service.expireAfterAccess("11111"));


        Thread.sleep(7000);
        log.info("====================================");
        log.info("7S后再访问，然后触发刷新 有replaced");
        log.info("getData  input:11111,  结果： {}", service.expireAfterAccess("11111"));

        Thread.sleep(20000);
        log.info("====================================");
        log.info("20S后再访问，然后触发访问过期 有expired");
        log.info("getData  input:11111,  结果： {}", service.expireAfterAccess("11111"));

        log.info("====================================");
        log.info("不停访问，只会有replaced");
        var pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleWithFixedDelay(() -> {
            log.info("getData  input:11111,  结果： {}", service.expireAfterAccess("11111"));
        }, 0, 1, TimeUnit.SECONDS);
        Helper.sleep(60_000);
        pool.shutdown();
    }
}