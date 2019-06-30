package yongfa365;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//都是单线程的，与C#行为不同(C#的timer每次都会开个线程)，java想实现C#的效果也很容易：方法内部再开个线程执行就行了。
//fixedDelay    不管执行多久，完成后都要等一会再执行。
//fixedRate      到点就执行，如果上一个没执行完就等着，上个完成后他就立即执行，会堆积。
//cron              到点就执行，如果上一个没执行完就跳过本次。

//https://segmentfault.com/a/1190000015253688 除了fixedRate说的不全外，其他都正确
//通过注释不用的来测试吧
@Slf4j
@EnableScheduling //要启用下
@SpringBootApplication
public class ScheduleInSpring {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleInSpring.class, args);
    }
    //Scheduled里long的单位都是ms，fixedRate、fixedDelay、cron同时只能有一个


    //到点就执行，执行前会判断：如果上一个还没执行完则本次就不执行了。
    //从0s开始每2s执行1次，以下例子会看到本来这些秒要执行的0,2,4,6,8,10，但有些跳过了，因为任务执行时间超时了，所以那次就跳过了。
    @Scheduled(cron = "*/2 * * * * *")
    public static void taskByCron() {
        log.info("taskByCron.start");
        Helper.maxSleep(5000);
        log.info("taskByCron.end");
    }

    //与jdk里的行为一致Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay
    //注意观察end与start的间隔是固定的，无论任务执行了多久都要等固定时间
    @Scheduled(fixedDelay = 3000, initialDelay = 3000)
    public static void taskByFixedDeplay() {
        log.info("taskByFixedDeplay.start");
        Helper.maxSleep(5000);
        log.info("taskByFixedDeplay.end");
    }


    //与jdk里的行为一致Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate
    //任务3s内完成则整体就是按3s的间隔在调度
    @Scheduled(fixedRate = 3000)
    public static void taskByFixedRate() {
        log.info("taskByFixedRate");
        Helper.maxSleep(2500);
    }

    //与jdk里的行为一致Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate
    //任务超过3s则，则上一个完成后紧接着就是执行下一个
    @Scheduled(fixedRate = 3000)
    public static void taskByFixedRate2() {
        log.info("taskByFixedRate2.start");
        Helper.sleep(5000);
        log.info("taskByFixedRate2.end");
    }

    private static boolean isFirst = false;

    //与jdk里的行为一致Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate
    //如果任务第一次要5s，之后只要0s，1s调度1次，则：第一次5s，然后紧接着会再执行4+次之前落下的，然后才1s1次
    @Scheduled(fixedRate = 1000)
    public static void taskByFixedRate3() {
        log.info("taskByFixedRate3");
        if (!isFirst) {
            Helper.sleep(5000);
            isFirst = true;
            log.info("taskByFixedRate3.第一次结束");
        }
    }


}


