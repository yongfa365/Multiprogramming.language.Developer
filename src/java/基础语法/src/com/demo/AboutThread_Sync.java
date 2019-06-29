package com.demo;

//线程同步
public class AboutThread_Sync {
    public static void main(String[] args) throws Exception {
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


    static class Temp<T1, T2> {
        public T1 Item1;
        public T2 Item2;
    }
}
