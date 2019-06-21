package yongfa365.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
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
public class VerifySpringBootCacheAnnotationServiceTest {

    @Autowired
    private VerifySpringBootCacheAnnotationService service;

        @Test
    public void 并发穿透_测试() throws Exception {
        log.info("10个并发请求 同时穿透，进入方法体了↓");

        var pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> log.info("数据：" + service.并发穿透()));
        }
        pool.shutdown();
        pool.awaitTermination(10,TimeUnit.SECONDS);
    }

    @Test
    public void 并发仅一个穿透_测试() throws  Exception {
        log.info("10个并发请求 仅1个穿透，其他等待有结果后直接返回↓");

        var pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> log.info("数据：" + service.并发仅一个穿透()));
        }
        pool.shutdown();
        pool.awaitTermination(10,TimeUnit.SECONDS);
    }

    @Test
    public void 首次访问或缓存过期后会穿透_测试() throws  Exception {
        log.info("首次访问or缓存过期后会穿透，会变慢，要等待，注意观察时间↓");

        var pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleWithFixedDelay(() -> {
            log.info("数据:" + service.首次访问或缓存过期后会穿透().toString());
        }, 0, 1, TimeUnit.SECONDS);

        //挂起线程，不然他以为执行完了就自己结束了。
        Helper.sleep(20_000);
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void 默认缓存容易互串_测试() throws Exception {
        log.info("默认缓存容易互串!!!,因为他的Key只是按方法参数计算的。");
        log.info("以下3个都是无参的，所以算出的缓存key也是相同的，后面两个无等待，直接从缓存返回");
        log.info("简单解决方法是显示的指定key：@cacheable(key=dsfsdfs)");
        log.info("数据：" + service.并发仅一个穿透());
        log.info("数据：" + service.并发穿透());
        log.info("数据：" + service.首次访问或缓存过期后会穿透());
    }

    @Test
    public void 带参数测试() {
        log.info("11111 数据：" + service.带参数测试("11111"));
        log.info("2222 数据：" + service.带参数测试("2222"));
        log.info("11111 数据：" + service.带参数测试("11111"));
    }

    @Test
    public void 带参数带固定key测试() {
        log.info("固定key会导致参数无效，所有参数都用同一个缓存");
        log.info("input:11111,  结果： {}" , service.带参数带固定key测试("11111"));
        log.info("input:22222,  结果： {}" , service.带参数带固定key测试("22222"));
        log.info("input:33333,  结果： {}" , service.带参数带固定key测试("33333"));
    }

    @After
    public  void 清除缓存()
    {
        service.清除所有同名称的缓存();
    }



}