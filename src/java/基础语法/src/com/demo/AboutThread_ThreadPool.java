package com.demo;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.demo.AboutThread.Run;

public class AboutThread_ThreadPool {
    public static void main(String[] args) throws Exception {
        //关于线程池，Java与C#最大的不同:
        // C#整个只有一个线程池。
        // Java可以定义任意多个线程池，只要内存CPU能承受得起。
        //相同的是：都可以设置最小最大线程数

        var cpuCoreNum = Runtime.getRuntime().availableProcessors();

        var threadPoolTypical = new ThreadPoolExecutor(
                cpuCoreNum, //最小线程数
                cpuCoreNum * 12, //最大线程数
                123456, TimeUnit.MILLISECONDS, //超过最小线程数的线程，空闲多久则回收
                new ArrayBlockingQueue<>(100, true)//放到某个队列里
        );

        //阿里不推荐用Executors,因为隐藏了要关注的核心细节
        var threadPool = Executors.newFixedThreadPool(10);
        //批量想pool添加任务，pool最多会使用10个线程去跑。
        for (int i = 0; i < 100; i++) {
            threadPool.submit(() -> Run());
        }
        //关门，不接客了。已接的客继续，是否服务完了不管。不等待，要等待得调用awaitTermination。
        threadPool.shutdown();
        //关门后，挂起线程最多多久，线程都结束了或超时了就不等了，至于线程是死是活不管（会有野线程在为所欲为）。
        threadPool.awaitTermination(10, TimeUnit.SECONDS);
    }
}
