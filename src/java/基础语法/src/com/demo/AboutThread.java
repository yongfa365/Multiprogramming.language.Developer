package com.demo;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class AboutThread {
    public static void main(String[] args) throws Exception {
        //开野线程的方法
        var thread1 = new Thread(() -> Run(), "我是线程元老");
        thread1.start(); //线程开始启动
        //thread1.run();//这个是同步的，不会新开线程，从线程名也可以看出来。
    }


    public static void Run() {
        System.out.printf("ThreadId::%s ThreadName：%s\r\n", Thread.currentThread().getId(), Thread.currentThread().getName());
    }
}