package com.demo.Helper;

import java.util.function.Consumer;

public class CodeTimer {
    public static void Time(String name, Runnable action) {
        var timer = System.currentTimeMillis();
        action.run();
        var total = System.currentTimeMillis() - timer;
        System.out.printf("\r\n%s 耗时:%s ms\r\n", name, total);
    }
}
