package com.demo;

import com.demo.Entity.Person;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class AboutCollection_Queue {
    public static void main(String[] args) throws Exception {
        var queue = new ArrayDeque<Person>();
        IntStream.range(1, 10).forEach(p -> queue.add(new Person(p, String.valueOf(p).repeat(3))));
        //既然是双向队列，那就可以两边操作
        queue.addFirst(new Person(11, "111"));
        queue.addLast(new Person(33, "333"));

        var count = queue.size();

        //这一堆弹出的操作，没有值会返回null,不会报错
        var item1 = queue.pop();
        var item2 = queue.poll();
        var item3 = queue.pollFirst();
        var item4 = queue.pollLast();

        queue.removeFirstOccurrence(queue.getLast());


        //线程安全的双向队列ConcurrentLinkedDeque<T>
        var conQueue = new ConcurrentLinkedDeque<Person>();
        IntStream.range(1, 10).forEach(p -> conQueue.add(new Person(p, String.valueOf(p).repeat(3))));
        //既然是双向队列，那就可以两边操作
        conQueue.addFirst(new Person(11, "111"));
        conQueue.addLast(new Person(33, "333"));
        var item5 = conQueue.pop();
        conQueue.clear();
        IntStream.range(1, 10).parallel().forEach(p -> conQueue.add(new Person(p, String.valueOf(p).repeat(3))));

        //线程安全的队列ConcurrentLinkedDeque<T>
        var conQueue2 = new ConcurrentLinkedQueue<Person>();
        conQueue2.add(new Person());
        var item6 = conQueue2.poll();
        IntStream.range(1, 10).parallel().forEach(p -> conQueue2.add(new Person(p, String.valueOf(p).repeat(3))));
        conQueue2.forEach(System.out::println);


        var delayQ = new DelayQueue<DelayedTask>();
        var now = LocalDateTime.now();
        delayQ.add(new DelayedTask(now, "立即处理"));
        delayQ.add(new DelayedTask(now.plusSeconds(3), "延迟3s处理"));
        delayQ.add(new DelayedTask(now.plusSeconds(5), "延迟5s处理"));

        while (delayQ.size() > 0) {
            var item = delayQ.take();
            System.out.println(LocalTime.now().toString() + item);
        }
    }

    static class DelayedTask implements Delayed {
        private LocalDateTime expireTime;
        private String msg;

        public DelayedTask(LocalDateTime expireTime, String msg) {
            this.expireTime = expireTime;
            this.msg = msg;
        }

        /**
         * 剩余时间=到期时间-当前时间
         */
        @Override
        public long getDelay(TimeUnit unit) {
            var result = unit.convert(ChronoUnit.MILLIS.between(LocalDateTime.now(), expireTime), TimeUnit.MILLISECONDS);
            return result;
        }

        /**
         * 优先队列里面优先级规则
         */
        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return String.format(" DelayedTask{expireTime=%s,  data='%s'}", DateTimeFormatter.ISO_TIME.format(expireTime), msg);
        }
    }

}
