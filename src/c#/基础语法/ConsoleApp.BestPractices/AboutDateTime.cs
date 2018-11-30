using System;
using System.Diagnostics;
using System.Threading;

namespace ConsoleApp.BestPractices
{
    public class AboutDateTime
    {
        ///<summary>
        ///有备注的直接看就行了
        ///没备注的只是说明其使用方法，自己调试断点F10一个一个看吧
        ///微软DateTime已经做的很好了，所以外界也几乎没有对他再二次开发的了
        ///官方：
        ///  完整文档：https://docs.microsoft.com/en-us/dotnet/api/system.datetime
        ///  标准format:https://docs.microsoft.com/en-us/dotnet/standard/base-types/standard-date-and-time-format-strings
        ///  自定义format:https://docs.microsoft.com/en-us/dotnet/standard/base-types/custom-date-and-time-format-strings
        ///</summary>
        public static void RunDemo()
        {
            #region 相关类
            //System.DateTime 日期与时间的，一般就用这个包括 日期，时间，时区 及各种计算方法,DateTime是struct，是值类型的
            //TimeSpan 只是日期时间的，比较少用，两个DateTime相减的结果就是这个
            //System.Diagnostics.Stopwatch 如其名，记录时间用的。
            #endregion


            #region DateTime.Now 的各种输出
            {
                var time1 = DateTime.Now;  //2018/11/21 16:16:31 ★之所以显示“/”而不是“-”是因为系统语言决定的,也许你的机器就是-
                var time2 = DateTime.UtcNow; //2018/11/21 8:16:31  减了8小时的
                var time3 = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fffffff"); //2018-11-21 16:16:31.2588022,f可以1-7个
                var time4 = DateTime.Now.ToString("o"); //2018-11-21T16:16:31.2658022+08:00 ★ISO8601，用这个哪都支持

                var now = DateTime.Now;
                Console.WriteLine(now.Date); //2018/11/21 0:00:00 也就是time部分全为0的结果

                Console.WriteLine(now.Year);
                Console.WriteLine(now.Month);
                Console.WriteLine(now.Day);
                Console.WriteLine(now.Hour);
                Console.WriteLine(now.Minute);
                Console.WriteLine(now.Second);
                Console.WriteLine(now.Millisecond);

                Console.WriteLine(now.Ticks);
                Console.WriteLine(now.Kind);
                Console.WriteLine(now.DayOfYear);
                Console.WriteLine(now.DayOfWeek);
                Console.WriteLine(now.TimeOfDay);
            }
            #endregion


            #region 定义DateTime 及 字符串转DateTime
            {
                var time1 = new DateTime(2018, 11, 21);
                var time2 = new DateTime(2018, 11, 21, 16, 28, 30);//就是年月日时分秒

                var tp_1 = DateTime.Parse("2018-11-21");
                var tp_2 = DateTime.Parse("2018-11-21 12:23:25");
                var tp_3 = DateTime.Parse("2018-11-21 12:23:25.12345");//7位的ms,java是9位的
                var tp_4 = DateTime.Parse("2018-11-21T12:23:25.1234567");
                var tp_5 = DateTime.Parse("2018-11-21T12:23:25.1234567+08:00");
                var tp_6 = DateTime.Parse("2018.11.21");
                var tp_7 = DateTime.Parse("12:23:25"); //日期是当天，时间是指定的。
                var tp_8 = DateTime.Parse("12:23");

                DateTime.TryParse("2018-11-21T12:23:25.1234567+08:00", out DateTime ttp_1); //out前面不用声明ttp_1
                Console.WriteLine(ttp_1);


            }
            #endregion


            #region DateTime增减、TimeSpan、比较大小
            {

                var time1 = DateTime.Now
                    .AddYears(1)
                    .AddMonths(-2) //可加可减
                    .AddDays(3)
                    .AddHours(2)
                    .AddMinutes(1)
                    .AddSeconds(1)
                    .AddMilliseconds(1)
                    .AddTicks(12345)
                  ;


                var timespan = DateTime.Now - DateTime.Now.AddDays(-1).AddHours(-2).AddMinutes(-3); //1.02:03:00
                Console.WriteLine(timespan);

                //TimeSpan比DateTime多了几个Total*属性，如：60s+1s的结果是：Minutes=1,Seconds=1，要看真实差得：TotalSeconds=61
                Console.WriteLine(timespan.TotalDays);
                Console.WriteLine(timespan.TotalHours);
                Console.WriteLine(timespan.TotalMinutes);
                Console.WriteLine(timespan.TotalSeconds);
                Console.WriteLine(timespan.TotalMilliseconds);

                Console.WriteLine(timespan.Days);
                Console.WriteLine(timespan.Hours);
                Console.WriteLine(timespan.Minutes);
                Console.WriteLine(timespan.Seconds);
                Console.WriteLine(timespan.Milliseconds);

                Console.WriteLine(timespan.Ticks);


                var timeBool1 = new DateTime(2018, 11, 21) == new DateTime(2018, 11, 21); //True
                var timeBool2 = DateTime.Now > DateTime.Now.AddDays(1); //False
                var timeBool3 = DateTime.Now < DateTime.Now.AddDays(1); //True
                var timeBool4 = DateTime.Now != DateTime.Now; //False

            }
            #endregion


            #region 计时器，Stopwatch
            {
                //看一段代码执行花了多久,方法1：
                var time = DateTime.Now;
                Thread.Sleep(1234);
                Console.WriteLine($@"耗时：{DateTime.Now - time}");

                //方法2:
                var sp = Stopwatch.StartNew();
                Thread.Sleep(1234);
                Console.WriteLine($@"sp.Elapsed: {sp.Elapsed} sp.ElapsedMilliseconds: {sp.ElapsedMilliseconds}");

                //Stopwatch的其他操作
                sp.Reset();
                sp.Restart();

                //以前的写法，现在也还能用
                var sp1 = new Stopwatch();
                sp1.Start();
                sp1.Stop();

            } 
            #endregion


            #region timer
            {
                var timer = new System.Timers.Timer
                {
                    Interval = 1000 * 1,
                    Enabled = true
                };

                timer.Elapsed += (sender, e) =>
                {
                    Console.WriteLine($"Timer Runing {timer.Interval++}");
                };
            } 
            #endregion

        }


    }
}
