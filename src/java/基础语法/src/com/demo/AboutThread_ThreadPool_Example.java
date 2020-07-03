package com.demo;


import com.demo.Helper.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;


public class AboutThread_ThreadPool_Example {
    public static void main(String[] args) throws Exception {
        //getResources_ByFuture();
        getResources_ByCountDownLatch();
    }

    private static void getResources_ByCountDownLatch() throws Exception {
        var threadPool = Executors.newFixedThreadPool(2);
        var countDownLatch = new CountDownLatch(2);
        var result = new ArrayList<Integer>();

        var start = System.currentTimeMillis();

       threadPool.execute(() -> {
            var hotelList = getHotelList();
            result.addAll(hotelList);
            countDownLatch.countDown();
        });

        threadPool.execute(() -> {
            var flightList = getFlightList();
            result.addAll(flightList);
            countDownLatch.countDown();
        });

        Helper.log("上面是异步的，所以会直接进入这里");

        countDownLatch.await();

        var end = System.currentTimeMillis();
        System.out.println("共耗时：" + (end - start) + "ms 所有资源：" + result);
    }

    private static void getResources_ByFuture() throws Exception {
        var threadPool = Executors.newFixedThreadPool(2);

        var result = new ArrayList<Integer>();

        var start = System.currentTimeMillis();

        var hotelTask = threadPool.submit(() -> {
            var hotelList = getHotelList();
            result.addAll(hotelList);
        });

        var flightTask = threadPool.submit(() -> {
            var flightList = getFlightList();
            result.addAll(flightList);
        });

        Helper.log("上面是异步的，所以会直接进入这里");

        hotelTask.get();
        Helper.log("酒店回来了");

        flightTask.get();
        Helper.log("机票回来了");

        var end = System.currentTimeMillis();
        System.out.println("共耗时：" + (end - start) + "ms 所有资源：" + result);
    }

    private static List<Integer> getHotelList() {
        Helper.sleep(3000);
        return List.of(1, 2, 3);
    }

    private static List<Integer> getFlightList() {
        Helper.sleep(8000);
        return List.of(4, 5, 6);
    }
}
