package com.demo;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class AboutThread {
    public static void RunDemo() throws Exception {

        //关于线程池，Java与C#最大的不同:
        // C#整个只有一个线程池。
        // Java可以定义任意多个线程池，只要内存CPU能承受得起。
        //相同的是：都可以设置最小最大线程数


        var thread1 = new Thread(() -> Run(), "我是线程元老");
        thread1.start(); //线程开始启动
        //thread1.run();//这个是同步的，不会新开线程，从线程名也可以看出来。

        //并行for；foreach
        IntStream.range(1, 10).parallel().forEach(System.out::println);

        var lst = List.of(1, 2, 3, 4, 5);
        lst.stream().forEach(System.out::println);


        //region 线程同步
        {
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
        //endregion


        var cpuCoreNum = Runtime.getRuntime().availableProcessors();
        var pool1 = new ThreadPoolExecutor(cpuCoreNum, cpuCoreNum * 12, 123456, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100, true));
        var threadPool = Executors.newFixedThreadPool(10); //据说阿里不推荐用Executors,因为隐藏了要关注的核心细节
        threadPool.submit(() -> Run());
        threadPool.submit(() -> Run());
        threadPool.shutdown();

    }


    public static void Run() {
        System.out.printf("ThreadId::%s ThreadName：%s\r\n", Thread.currentThread().getId(), Thread.currentThread().getName());
    }

    static class Temp<T1, T2, T3, T4> {
        public T1 Item1;
        public T2 Item2;
        public T3 Item3;
        public T4 Item4;
    }
}
