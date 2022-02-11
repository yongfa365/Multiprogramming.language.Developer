package com.demo;


import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

// 并行，表面上看很简单.parallel()或者.stream().parallel()或者.parallelStream()就行
// 实际使用要特别注意，他内部用的是ForkJoinPool.commonPool(),整个应用都用这么一个,
// 最大并行度是CPU核数-1,如果你将一些IO操作放这里，会造成任务堆积，严重影响其他用到ForkJoinPool的操作。
// 可以让他运行在自定义的Pool里：new ForkJoinPool(10),用完后记得shutdown(),不然会内存泄漏
public class AboutThread_Parallel {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        IntStream.range(1, 10).parallel().forEach(p -> {
            System.out.printf("%s item:%s%n", Thread.currentThread().getName(), p);
        });

        List.of(1, 2, 3, 4, 5).stream().parallel().forEach(p -> {
            System.out.printf("%s item:%s%n", Thread.currentThread(), p);
        });

        List.of(1, 2, 3, 4, 5).parallelStream().forEach(p -> {
            System.out.printf("%s item:%s%n", Thread.currentThread(), p);
        });


        // 让parallel使用自定义的ForkJoinPool也很简单
        // 原理：https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/ForkJoinTask.html#fork()
        new ForkJoinPool(15).submit(() -> {
            IntStream.range(1, 100).parallel().forEach(p -> {
                System.out.printf("%s item:%s%n", Thread.currentThread(), p);
            });
        }).join();


        // 上面的写法会导致内存泄漏，解释：https://www.baeldung.com/java-8-parallel-streams-custom-threadpool#beware-of-the-memory-leak
        // 解决方案：用完后shutdown下
        var forkJoinPool = new ForkJoinPool(15);
        try {
            forkJoinPool.submit(() -> {
                IntStream.range(1, 100).parallel().forEach(p -> {
                    System.out.printf("%s item:%s%n", Thread.currentThread(), p);
                });
            }).join();
        } finally {
            forkJoinPool.shutdown();
        }

        // 封装个方法测试下内存泄漏的问题
        for (int i = 0; i < 100000; i++) {
            parallelRun(100, () -> {
                IntStream.range(1, 100).parallel().forEach(p -> {
                    System.out.printf("%s item:%s%n", Thread.currentThread(), p);
                });
            });
        }


    }

    /**
     * runable里的要用parallelStream，不然没有并行效果
     * @param parallelism
     * @param task
     */
    static void parallelRun(int parallelism, Runnable task) {
        var forkJoinPool = new ForkJoinPool(parallelism);
        try {
            forkJoinPool.submit(task).join();
        } finally {
            // 注释掉会导致内存泄漏，可以在任务管理器看到内存只升不降。
            forkJoinPool.shutdown();
        }
    }
}
