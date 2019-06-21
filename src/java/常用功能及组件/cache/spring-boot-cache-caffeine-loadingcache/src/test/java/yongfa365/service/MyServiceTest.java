package yongfa365.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import yongfa365.common.Helper;

import java.util.UUID;
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
        while (true) {
            log.info("getData  input:11111,  结果： {}", service.getData("11111"));
            log.info("getData  input:22222,  结果： {}", service.getData("22222"));
            Helper.sleep(1000);
        }
    }

    @Test
    public void getData_穿透测试() {

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                log.info("getData {}", service.getData("11111"));
            }).start();;


        }
        Helper.sleep(20_000);

    }

    @Test
    public void getData2() {
        var pool = Executors.newSingleThreadScheduledExecutor();
        pool.scheduleWithFixedDelay(() -> {
            log.info("getData2 {}", service.getData2("234234"));
        }, 0, 1, TimeUnit.SECONDS);
        Helper.sleep(50_000);
        pool.shutdown();
    }
}