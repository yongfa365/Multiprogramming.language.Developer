package com.demo.Others;

public class AboutThreadLocal {
    // ThreadLocal的get,set里会涉及初始化的操作：
    //     声明时用：new ThreadLocal<>()，则：初始化创建的内容是默认的null，
    //     声明时用：ThreadLocal.withInitial(()->{})，则：初始化创建的内容是lambda返回的。
    // 在某个Thread里get或set时，就是针对那个Thread的字段的操作，所以根本不用锁，ThreadLocal本质就是个Helper类。
    // Thread里的字段的定义：ThreadLocal.ThreadLocalMap threadLocals=null

    // 内部实现：
    // ThreadLocalMap.table是个数组，默认有16个槽位，也就是最多能存16个ThreadLocal变量，
    // 如果冲突了则会先遍历槽位，并清理没引用的，然后将本次冲突的对象放到一个非自己的空的槽位上，
    // 获取时也是在自己的槽位找不到时则遍历其他槽位找。当槽位达到2/3时会扩容为之前的两倍，并对旧数据重新分配槽位。


    // ThreadLocal和线程池一起使用？要考虑内存泄漏及数据互串的问题
    // ThreadLocal对象的生命周期跟线程的生命周期一样长，
    // 那么如果将ThreadLocal对象和线程池一起使用，就可能会遇到这种情况：
    // 一个线程的ThreadLocal对象会和其他线程的ThreadLocal对象串掉，一般不建议将两者一起使用。
    // 当然也是有解决方案的1：使用完后remove();2：https://github.com/alibaba/transmittable-thread-local

}