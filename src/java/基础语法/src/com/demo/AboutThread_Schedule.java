package com.demo;


import com.demo.Helper.Helper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AboutThread_Schedule {
    public static void main(String[] args) {
        //都是 上一个跑完后，才跑下一个，本质上是同步的（无论线程池设置多大），与C#行为不同(C#的timer每次都会开个线程)，
        //java想实现C#的效果也很容易，方法内部再开个线程执行就行了

        //方法1:原生timer，不推荐
        var timer = new Timer("我是个Timer", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(LocalTime.now() + "Timer Runing ");
            }
        }, 0, 1000);

        //方法2：按固定频率调度，类似令牌桶，到时间就往桶里扔一个，不管桶里有多少个，worker只要看到桶里有就执行。
        //上一次任务的开始时间 到 下一次任务的开始时间 的间隔，每次任务都会执行；
        // 如果 任务执行时间>间隔时间 则当前完成后立即执行下一次，否则间隔时间到了才执行。
        // 如果任务第一次要5s，之后只要0s，间隔设置为1s，则：第一次5s，然后紧接着会再执行4次之前落下的，然后才1s1次
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println("scheduleAtFixedRate:" + LocalDateTime.now());
            Helper.sleep(1000); //改成1000及5000分别试下
        }, 0, 3, TimeUnit.SECONDS);

        //方法3：按固定间隔调度，不管任务执行多久，按执行完后，都得等固定间隔再执行下一次。
        //上一次任务的结束时间 到 下一次任务的开始时间 的间隔，每次任务都会执行；
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
            System.out.println("scheduleAtFixedRate:" + LocalDateTime.now());
            Helper.sleep(1000); //改成1000及5000分别试下
        }, 0, 3, TimeUnit.SECONDS);

    }


}
