package com.demo.Others;


import com.demo.Helper.Helper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class AboutThreadLocal_DateFormatter {
    public static void main(String[] args) {
        //Demo SimpleDateFormat 有问题的场景
        var formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 100; i++) {
            final var x = i;
            new Thread(() -> {
                Date date = Helper.GetDate(LocalDate.now().plusDays(x));
                var date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                var date2 = formatter.format(date);
                if (!date1.equals(date2)) {
                    System.out.println(String.format("不安全的%s====%s\t", date1, date2));
                }
            }).start();
        }


        //Demo SimpleDateFormat 使用threadlocal后正常了
        for (int i = 0; i < 100; i++) {
            final var x = i;
            new Thread(() -> {
                Date date = Helper.GetDate(LocalDate.now().plusDays(x));
                var date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                var date2 = DateFormatter.format(date);
                if (!date1.equals(date2)) {
                    System.out.println(String.format("安全的%s====%s\t", date1, date2));
                }
            }).start();
        }
    }
}


//Demo 1：解决SimpleDateFormat线程不安全的问题
class DateFormatter {
    // SimpleDateFormat is not thread-safe, so give one to each thread
    // 每个线程第一次调用的时候就给他初始化一个
    private static final ThreadLocal<SimpleDateFormat> formatter =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static String format(Date date) {
        return formatter.get().format(date);
    }
}