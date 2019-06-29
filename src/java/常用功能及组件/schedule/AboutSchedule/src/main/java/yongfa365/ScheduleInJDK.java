package yongfa365;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduleInJDK {
    private static Random RANDOM = new Random();

    @Test
    public void jdk_Timer() throws Exception {
        //方法1:原生timer，不推荐
        var timer = new Timer("I'm a Timer", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("jdk_Timer");
            }
        }, 0, 1000 * 1);

        Helper.sleep(5_000);
        timer.cancel();
    }

    @Test
    public void jdk_ScheduleAtFixedRate() throws InterruptedException {
        //方法2：按固定频率调度，类似令牌桶，到时间就往桶里扔一个，不管桶里有多少个，worker只要看到桶里有就一个接一个执行而不等待。
        // 如果 任务执行时间>间隔时间 则当前完成后立即执行下一次，否则间隔时间到了才执行。
        // 如果任务第一次要5s，之后只要0s，间隔设置为1s，则：第一次5s，然后紧接着会再执行4次之前落下的，然后才1s1次

        var scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            log.info("jdk_ScheduleAtFixedRate");
            Helper.sleep(1000); //改成1000及5000分别试下
        }, 0, 3, TimeUnit.SECONDS);


        Helper.sleep(30_000);
        scheduler.shutdown();
        scheduler.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void jdk_ScheduleWithFixedDelay() throws InterruptedException {
        //方法3：按固定间隔调度，不管任务执行多久，按执行完后，都得等固定间隔再执行下一次。
        log.info("注意观察end与start的间隔是固定的，无论任务执行了多久都要等固定时间");


        var scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(() -> {
            log.info("jdk_ScheduleWithFixedDelay.start");
            Helper.sleep(RANDOM.nextInt(5000));
            log.info("jdk_ScheduleWithFixedDelay.end");
        }, 0, 3, TimeUnit.SECONDS);


        Helper.sleep(20_000);
        scheduler.shutdown();
        scheduler.awaitTermination(5, TimeUnit.SECONDS);
    }


}
