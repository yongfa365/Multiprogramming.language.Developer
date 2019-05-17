package com.demo.Others;


import java.util.concurrent.atomic.AtomicInteger;

public class AboutThreadLocal_ThreadId {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(ThreadId.get());
            }).start();
        }
    }
}


//Demo 2：生成 全局线程Id
class ThreadId {
    // Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId = new AtomicInteger(100);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId =
            ThreadLocal.withInitial(() -> nextId.getAndIncrement());

    // Returns the current thread's unique ID, assigning it if necessary
    public static int get() {
        return threadId.get();
    }
}
