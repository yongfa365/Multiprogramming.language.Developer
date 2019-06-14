package yongfa365.springbootcachecaffeine;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheHelperTest {

    @Autowired
    private CacheHelper cacheHelper;


    @Test
    public void 并发穿透测试() throws Exception {
        log.info("10个并发请求 同时穿透，进入方法体了↓");

        var pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> log.info("数据：" + cacheHelper.并发穿透测试()));
        }
        pool.shutdown();
        pool.awaitTermination(10,TimeUnit.SECONDS);
    }

    @Test
    public void 并发穿透测试_解决() throws  Exception {
        log.info("10个并发请求 仅1个穿透，其他等待有结果后直接返回↓");

        var pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> log.info("数据：" + cacheHelper.并发穿透测试_解决()));
        }
        pool.shutdown();
        pool.awaitTermination(10,TimeUnit.SECONDS);
    }

    @Test
    public void 首次访问或缓存过期后会穿透() throws  Exception {
        log.info("首次访问or缓存过期后会穿透，会变慢，要等待，注意观察时间↓");

        var pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleWithFixedDelay(() -> {
            log.info("数据:" + cacheHelper.首次访问或缓存过期后会穿透().toString());
        }, 0, 1, TimeUnit.SECONDS);

        //挂起线程，不然他以为执行完了就自己结束了。
        cacheHelper.sleep(20_000);
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }

    @After
    public  void 清除缓存()
    {
        cacheHelper.清除所有同名称的缓存();
    }
}