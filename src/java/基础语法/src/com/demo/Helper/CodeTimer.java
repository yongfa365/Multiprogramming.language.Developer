package com.demo.Helper;

import java.util.function.Consumer;

public class CodeTimer {
    public static void Time(String name, Consumer action) {
        var timer = System.currentTimeMillis();
        action.accept("");
        var total = System.currentTimeMillis() - timer;
        System.out.printf("\r\n%s 耗时:%s ms\r\n", name, total);
    }
}
