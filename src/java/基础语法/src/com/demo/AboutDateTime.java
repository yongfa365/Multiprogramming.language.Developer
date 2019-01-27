package com.demo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AboutDateTime {
    public static void main(String[] args) throws Exception {
        RunDemo();
    }

    public static void RunDemo() throws Exception {

        //region 相关类 与C#大不同,研究这个花了一天多时间

        //1.8之前的，位于java.util中，初始化后还可以set改变，改变后getxxx时才生效，线程不安全，被各种吐槽，不推荐：
        //Date 大量方法已经标记@deprecated，
        //Calendar 也有各种缺陷

        //1.8后新增加的，位于java.time包中，这里的类是不可变的且线程安全的，toString()默认都是ISO的，推荐：
        //Instant：表示时刻，不直接对应年月日信息，需要通过时区转换
        //LocalDateTime: 表示与时区无关的日期和时间信息，不直接对应时刻，需要通过时区转换
        //LocalDate：表示与时区无关的日期，与LocalDateTime相比，只有日期信息，没有时间信息
        //LocalTime：表示与时区无关的时间，与LocalDateTime相比，只有时间信息，没有日期信息
        //ZonedDateTime： 表示特定时区的日期和时间
        //ZoneId/ZoneOffset：表示时区

        //endregion


        //region now 的各种输出
        {
            //日期时间类型比较多，都写上了，后面主要关注ZonedDateTime
            var now_date = LocalDate.now(); //2018-11-28
            var now_time = LocalTime.now(); //16:57:42.205617700
            var now_datetime = LocalDateTime.now(); //2018-11-28T16:57:42.205617700
            var now_zoned = ZonedDateTime.now(); //2018-11-28T16:57:42.205617700+08:00[Asia/Shanghai]
            var now_instant = Instant.now(); //2018-11-28T08:57:42.206617700Z  减了8小时的,这个就是0时区的时间啦UtcNow。


            var now = ZonedDateTime.now();
            var time2 = ZonedDateTime.now().toString(); //默认就是ISO标准，2018-12-01T16:58:20.972347500+08:00[Asia/Shanghai]
            var time3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n").format(now); //纳秒，C#可以指定1-7个f,java不能指定位数
            var time4 = now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME); //2018-11-28T16:57:42.205617700+08:00 ★ISO8601，用这个哪都支持
            var time5 = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n")); //java8之前有个SimpleDateFormat，非线程安全的，不要用。

            var now_1 = now.toLocalDate();
            var now_2 = now.getYear();
            var now_3 = now.getMonth().getValue(); //getMonth()返回的是个Month的enum有name有value
            var now_4 = now.getDayOfMonth();
            var now_5 = now.getHour();
            var now_6 = now.getMinute();
            var now_7 = now.getSecond();
            var now_8 = now.getNano();
            var now_9 = now.getDayOfYear();
            var now_10 = now.getDayOfWeek().getValue(); // getDayOfWeek()返回的是个DayOfWeek的enum有name有value
            var now_11 = now.getZone();

            System.out.println("断点用");
        }
        //endregion


        //region 定义DateTime 及 字符串转DateTime
        {
            var time1 = LocalDate.of(2018, 11, 21);
            var time2 = ZonedDateTime.of(2018, 11, 21, 12, 23, 23, 123456700, ZoneId.of("+08:00"));
            var time3 = LocalTime.of(12, 23, 23, 123456700);
            var time4 = LocalDateTime.of(2018, 11, 21, 12, 23, 23, 123456700);//有多个重载

            //日期格式：https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/format/DateTimeFormatter.html
            //默认的parse只支持“对应的标准的ISO格式“，如果想支持别的，可以使用重载方法指定字符串类型
            var tp_1 = LocalDate.parse("2018-11-21");
            var tp_4 = LocalDateTime.parse("2018-11-21T12:23:25");
            var tp_3 = LocalDateTime.parse("2018-11-21T12:23:25.1234567");
            var tp_5 = ZonedDateTime.parse("2018-11-21T12:23:25.1234567+08:00");
            var tp_7 = LocalTime.parse("12:23:25");
            var tp_8 = LocalTime.parse("12", DateTimeFormatter.ofPattern("HH"));

            var tp_9 = ZonedDateTime.parse(
                    "2018年11月21日T12时23分25秒123456700纳秒时区+08:00"
                    , DateTimeFormatter.ofPattern("yyyy年MM月dd日'T'HH时mm分ss秒n纳秒时区xxx")); //时区用几个 x或别的要看文档


            System.out.println("断点用");

        }
        //endregion


        //region DateTime增减、比较大小
        {

            var time1 = ZonedDateTime.now()
                    .plusYears(1)
                    .plusMonths(-2) //可加可减
                    .plusDays(3)
                    .minusHours(-2)
                    .minusMinutes(1)
                    .minusSeconds(1)
                    .minusNanos(1)
                    .minus(1, ChronoUnit.SECONDS);//指定改哪个


            //Period只能比较日期，如果有时间你要先自行修整下才能比较
            var period = Period.between(ZonedDateTime.now().toLocalDate(), ZonedDateTime.now().plusYears(1).plusMonths(2).plusDays(3).toLocalDate()); //P1Y2M3D
            var period_Y = period.getYears(); // 1 只考虑年
            var period_M = period.getMonths(); //2 只考虑月的数字，没有包含年
            var period_D = period.getDays(); //3 只考虑天的数字，没有包含年及月
            var period_diff_d = period.toTotalMonths(); //14 这个是算总的

            //Duration完整的日期时间去比较，结果都折算成时间，然后在时间维度考虑，如：
            var duration = Duration.between(ZonedDateTime.now(), ZonedDateTime.now().plusDays(-1).plusHours(-2).plusMinutes(-3)); //PT-26H-3M
            var duration_D = duration.toDays(); //-1
            var duration_H = duration.toHours(); //-26 算的是总数，而不是只算Hour部分
            var duration_M = duration.toMinutes(); // 算的是总数
            var duration_S = duration.toSeconds(); // 算的是总数
            var duration_MS = duration.toMillis(); // 算的是总数

            //★ChronoUnit,如果最终只是想要看两个时间的具体差值，这个就是首选啦。arg2-arg1的结果,当然也是有些限制的，具体自行debug吧。
            var days = ChronoUnit.DAYS.between(ZonedDateTime.now().plusYears(1), ZonedDateTime.now()); //-365
            var seconds = ChronoUnit.SECONDS.between(ZonedDateTime.now(), ZonedDateTime.now().plusYears(1)); //31536000
            var ms = ChronoUnit.MILLIS.between(ZonedDateTime.now(), ZonedDateTime.now().plusSeconds(1)); //1000

            //比较大小
            var nowdate = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ISO_DATE)); //用字符串构建一个当前日期
            var tiemBool1 = nowdate.isEqual(LocalDate.now()); //true  如果要比较!=，就取一下!吧。
            var timeBefore = ZonedDateTime.now().isBefore(ZonedDateTime.now().plusYears(1));
            var timeAfter = ZonedDateTime.now().isAfter(ZonedDateTime.now().plusYears(1));

            System.out.println("断点用");

        }
        //endregion


        //region 计时器，Stopwatch
        {
            //★看一段代码执行花了多久,方法1：
            var timer11 = System.currentTimeMillis();
            Thread.sleep(1000);
            var timer22 = System.currentTimeMillis();
            System.out.println("耗时：" + (timer22 - timer11) + "ms");

            //方法2:原生没有StopWatch,可以用org.apache.commons.lang3.time


        }
        //endregion


        //region timer
        {
            var timer = new Timer("我是个Timer", true);

            timer.schedule(new TimerTask() {
                public void run() {
                    System.out.println(LocalTime.now().toString() + "Timer Runing ");
                }

            }, 1000 * 1, 1000 * 1);

        }
        //endregion


        //region java8之前的Date、Calendar,不推荐使用
        {
            var cal = Calendar.getInstance();
            cal.set(2018, 11, 28); //设置后不是立即生效的
            cal.getTime(); //调用后才生效。

            var cal2 = cal;

            cal.set(2019, 11, 28);
            cal.getTime(); //改了123，234也变了


            var oldDate = new Date(2018, 12, 13);
            var xxxxx = oldDate.getTime();

            var time__02 = oldDate;

            oldDate.setTime(123456789);
            oldDate.getTime();
            var xxxxxx = oldDate.getTime();
        }
        //endregion

    }
}
