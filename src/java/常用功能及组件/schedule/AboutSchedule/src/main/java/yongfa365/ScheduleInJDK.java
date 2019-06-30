package yongfa365;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduleInJDK {

    //========================= Timer =======================================
    @Test
    public void jdk_Timer() throws Exception {
        // 原生timer，不推荐
        var timer = new Timer("I'm a Timer", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("jdk_Timer");
            }
        }, 0, 1000 * 1);

        waitThenStop(null, timer, 5_000);
    }

    //========================= scheduleWithFixedDelay =======================================
    @Test
    public void jdk_ScheduleWithFixedDelay() throws InterruptedException {
        // 按固定间隔调度，不管任务执行多久，按执行完后，都得等固定间隔再执行下一次。
        log.info("注意观察end与下一个start的间隔是固定的，无论任务执行了多久");

        var scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(() -> {
            log.info("jdk_ScheduleWithFixedDelay.start");
            Helper.maxSleep(5000);
            log.info("jdk_ScheduleWithFixedDelay.end");
        }, 0, 3000, TimeUnit.MILLISECONDS);

        waitThenStop(scheduler, null, 25_000);
    }


    //========================= scheduleAtFixedRate =======================================
    // 按固定频率调度，类似用令牌桶来控制频率，到时间就往桶里扔一个，不管桶里有多少个，worker只要看到桶里有就一个接一个执行而不等待。
    // 如果 任务执行时间>间隔时间 则当前完成后立即执行下一次，否则间隔时间到了才执行。
    // 如果任务第一次要5s，之后只要0s，间隔设置为1s，则：第一次5s，然后紧接着会再执行4次之前落下的，然后才1s1次


    @Test
    public void jdk_ScheduleAtFixedRate() throws InterruptedException {
        log.info("任务3s内完成则整体就是按3s的间隔在调度");

        var scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            log.info("jdk_ScheduleAtFixedRate");
            Helper.maxSleep(2500);
        }, 0, 3000, TimeUnit.MILLISECONDS);

        //防止程序终止，等一会手动终止
        waitThenStop(scheduler, null, 15_000);
    }

    @Test
    public void jdk_ScheduleAtFixedRate2() throws InterruptedException {
        log.info("任务超过3s则，则上一个完成后紧接着就是执行下一个");

        var scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            log.info("jdk_ScheduleAtFixedRate2.start");
            Helper.sleep(5000);
            log.info("jdk_ScheduleAtFixedRate2.end");
        }, 0, 3000, TimeUnit.MILLISECONDS);


        waitThenStop(scheduler, null, 30_000);
    }

    private boolean isFirst = false;

    @Test
    public void jdk_ScheduleAtFixedRate3() throws InterruptedException {
        log.info("如果任务第一次要5s，之后只要0s，1s调度1次，则：第一次5s，然后紧接着会再执行4+次之前落下的，然后才1s1次");

        var scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            log.info("jdk_ScheduleAtFixedRate3");
            if (!isFirst) {
                Helper.sleep(5000);
                isFirst = true;
                log.info("jdk_ScheduleAtFixedRate3.第一次结束");
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        //防止程序终止，等一会手动终止
        waitThenStop(scheduler, null, 30_000);
    }


    private void waitThenStop(ScheduledExecutorService scheduler, Timer timer, int sleep) throws InterruptedException {
        //防止程序终止，等一会手动终止
        Helper.sleep(sleep);
        if (scheduler != null) {
            scheduler.shutdown();
            scheduler.awaitTermination(5, TimeUnit.SECONDS);
        } else if (timer != null) {
            //防止程序终止，等一会手动终止
            Helper.sleep(5_000);
            timer.cancel();
        }
    }

}
