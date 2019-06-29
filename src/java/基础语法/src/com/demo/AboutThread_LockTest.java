package com.demo;

import java.util.stream.IntStream;

/**
 * @auther yongfa365
 * 2019/6/29 21:30
 */
class AboutThread_LockTest {
    public static void main(String[] args) {
        new AboutThread_LockTest().LockDemo();
    }

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
