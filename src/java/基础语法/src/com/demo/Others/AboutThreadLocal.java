package com.demo.Others;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class AboutThreadLocal {
    //ThreadLocal和线程池一起使用？
    //ThreadLocal对象的生命周期跟线程的生命周期一样长，那么如果将ThreadLocal对象和线程池一起使用，就可能会遇到这种情况：一个线程的ThreadLocal对象会和其他线程的ThreadLocal对象串掉，一般不建议将两者一起使用。

    public static void main(String[] args) {

        //Demo SimpleDateFormat 有问题的场景
        var formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 100; i++) {
            final var x = i;
            new Thread(() -> {
                var date = new Date(2019, 1, x);
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
                var date = new Date(2019, 1, x);
                var date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                var date2 = DateFormat.format(date);
                if (!date1.equals(date2)) {
                    System.out.println(String.format("安全的%s====%s\t", date1, date2));
                }
            }).start();
        }

        //Demo ThreadId
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(ThreadId.get());
            }).start();
        }

        //Demo ContextUser功能
        for (int i = 0; i < 5; i++) {
            final var x = String.valueOf(i);
            new Thread(() -> {
                var user = ContextUser.getUser();
                ContextUser.setStatus(x);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(x + "=======" + ContextUser.echo());
            }).start();
        }
    }


}


//Demo 1：解决SimpleDateFormat线程不安全的问题
class DateFormat {
    // SimpleDateFormat is not thread-safe, so give one to each thread
    private static final ThreadLocal<SimpleDateFormat> formatter =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static String format(Date date) {
        return formatter.get().format(date);
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


//Demo 3：实现类似HttpContext的功能，参考：http://www.jasongj.com/java/threadlocal/
class ContextUser {
    private static ThreadLocal<Session> session = ThreadLocal.withInitial(() -> new Session());

    public static String getUser() {
        return session.get().user;
    }

    public static void setStatus(String status) {
        session.get().status = status;
    }

    public static String echo() {
        var item = session.get();
        return String.format("{id:%s, user:%s status:%s}", item.id, item.user, item.status);
    }

    static class Session {
        Long id = System.currentTimeMillis();
        String user = "User" + id;
        String status = "Online";

        Session() {
        }


    }


}