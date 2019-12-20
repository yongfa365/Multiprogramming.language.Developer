package com.demo;


import com.demo.Helper.Helper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.demo.AboutThread.Run;

@SuppressWarnings("ALL")
public class AboutThread_ThreadPool {
    public static void main(String[] args) throws Exception {
        //关于线程池，Java与C#最大的不同:
        // C#整个只有一个线程池。
        // Java可以定义任意多个线程池，只要内存CPU能承受得起。
        //相同的是：都可以设置最小最大线程数

        var cpuCoreNum = Runtime.getRuntime().availableProcessors();

        var threadPoolTypical = new ThreadPoolExecutor(
                //最小线程数
                100,
                //最大线程数
                300,
                //超过最小线程数的线程，空闲多久则回收
                123456, TimeUnit.MILLISECONDS,
                //这里的数量并不是最大线程数，而是：超过最小线程数后就会放这个队列里的数量，放满后才会再开线程达到最大线程数，再超过的reject掉
                new ArrayBlockingQueue<>(50, true)
        );

        //此例子，threadCount<=350正常，>350则会RejectedExecutionException，350 = maximumPoolSize + workQueue.size()
        var threadCount = 350;
        for (int i = 0; i < threadCount; i++) {
            threadPoolTypical.submit(() -> {
                Helper.sleep(10_000);
                Run();
            });
        }


        //开启所有核心线程，然后待命。默认逻辑是：需要线程时才启动线程。
        threadPoolTypical.prestartAllCoreThreads();

        //核心线程是否回收，默认false表示不回收。
        threadPoolTypical.allowCoreThreadTimeOut(true);

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

/////////////////////////////////////////// 线程池运行流程，简单解析 ///////////////////////////////////////////
//假设：线程池设置为min=100,max=300,queue=50，突然来了800个并发

//场景1：典型的线程池的流程：
// 启动100个核心线程处理任务(1-100)-->接下来50个任务(101-150)放队列-->接下来再启动200个线程(max-min)处理后续任务(151-350)-->其他450个任务(351-800)reject掉-->处理完后超过keepAliveTime则回收200个线程，保留100个核心线程待命。
// 300       ________
// 200      /        \
// 100     /          \______
// 000____/

//场景2：tomcat里的默认线程池顺序不同，他重写了队列的offer的算法且默认queue=max，流程：
// 先启动100个核心线程待命，并发来后-->100个任务放队列(1-100)-->接下来再启动200个线程(max-min)处理后续任务(101-300)-->其他任务(301-800)加入队列-->处理完后超过keepAliveTime则回收200个线程，保留100个核心线程待命。
// 300       ________
// 200      /        \
// 100_____/          \______
// 000
