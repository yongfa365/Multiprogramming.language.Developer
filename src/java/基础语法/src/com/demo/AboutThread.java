package com.demo;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class AboutThread {
    public static void RunDemo() throws Exception {

        RunOldThreadDemo();

        RunThreadPoolDemo();

        RunParallelDemo();

        RunThreadSyncDemo();

        new LockTest().LockDemo();
    }

    /**
     * 开野线程的方法
     */
    private static void RunOldThreadDemo() {
        var thread1 = new Thread(() -> Run(), "我是线程元老");
        thread1.start(); //线程开始启动
        //thread1.run();//这个是同步的，不会新开线程，从线程名也可以看出来。
    }

    /**
     * ThreadPool的用法，
     */
    private static void RunThreadPoolDemo() {
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
        threadPool.submit(() -> Run());
        threadPool.submit(() -> Run());
        threadPool.shutdown();
    }

    /**
     * 并行，很简单.parallel()就行
     */
    private static void RunParallelDemo() {
        IntStream.range(1, 10).parallel().forEach(System.out::println);

        List.of(1, 2, 3, 4, 5).stream().parallel().forEach(System.out::println);
    }

    /**
     * 线程同步
     *
     * @throws Exception
     */
    private static void RunThreadSyncDemo() throws Exception {
        var sw = System.currentTimeMillis();
        var items = new Temp();
        //获取酒店数量、列表
        var getHotels = new Thread(() -> {
            try {
                Thread.sleep(1234); //模拟查询酒店......
                items.Item1 = 100;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //获取机票数量、列表
        var getFlights = new Thread(() -> {
            try {
                Thread.sleep(2000); //模拟查询机票......
                items.Item2 = 200;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //开始跑吧
        getHotels.start();
        getFlights.start();
        //等机票酒店都回来,C#的Wait()是等完成,Java的wait()不是，得用join();
        getHotels.join();
        getFlights.join();

        //返回结果：
        System.out.printf("查询耗时%s ms,酒店%s个，机票%s个，可供你选择\r\n", System.currentTimeMillis() - sw, items.Item1, items.Item2);
    }


    public static void Run() {
        System.out.printf("ThreadId::%s ThreadName：%s\r\n", Thread.currentThread().getId(), Thread.currentThread().getName());
    }


}

class Temp<T1, T2, T3, T4> {
    public T1 Item1;
    public T2 Item2;
    public T3 Item3;
    public T4 Item4;
}

class LockTest {
    private static final Object objLock = new Object();

    private int addSync = 0;
    private int addAsync = 0; //没锁，结果就可能不对
    private int addByMethodSynchronized = 0;
    private int addBySynchronizedObject = 0;

    void LockDemo() {
        int count = 10001;

        //演示：同步操作
        IntStream.range(1, count).forEach(i -> {
            addSync += 1;
        });

        //演示：异步操作，没有使用lock时，最终的结果不对
        IntStream.range(1, count).parallel().forEach(i -> {
            addAsync += 1;
        });
        var nolock = addSync == addAsync; //false

        //演示：异步操作，使用方法一级的锁synchronized，最终结果正确
        IntStream.range(1, count).parallel().forEach(this::SyncAdd);
        var withlock_Synchronized = addSync == addByMethodSynchronized; //true

        //演示：异步操作，使用代码段一级的锁synchronized，最终结果正确
        IntStream.range(1, count).parallel().forEach(i -> {
            synchronized (objLock) {
                addBySynchronizedObject += 1;
            }
        });
        var withlock_Synchronized2 = addSync == addBySynchronizedObject; //true

        //还有一堆其他锁：
        //https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/locks/package-summary.html
        //https://winterbe.com/posts/2015/04/30/java8-concurrency-tutorial-synchronized-locks-examples/
    }

    private synchronized void SyncAdd(int i) {
        addByMethodSynchronized += 1;
    }
}
