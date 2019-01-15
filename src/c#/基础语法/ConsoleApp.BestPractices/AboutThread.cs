using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Threading;
using System.Threading.Tasks;
using System.Linq;

namespace ConsoleApp.BestPractices
{
    /// <summary>
    /// 虽然在C#里写并行或开线程很容易，基本就一行代码，但增加线程来加速的做法不可取。
    /// 建议正式使用时还是用专业的消息队列吧，如：RabbitMQ,Kafka
    /// 官网：https://docs.microsoft.com/en-us/dotnet/api/system.threading.tasks
    /// 官网：https://docs.microsoft.com/en-us/dotnet/api/system.threading.thread
    /// </summary>
    public class AboutThread
    {
        public static void RunDemo()
        {
            RunOldThreadDemo();

            RunThreadPoolGlobalSettingsDemo();

            RunTypical_Thread_Task_Parallel_Demo();

            RunThreadSyncDemo();

            LockTest.LockDemo();
        }

        /// <summary>
        /// 早期开线程的方法，不建议研究
        /// </summary>
        private static void RunOldThreadDemo()
        {
            //new Thread开的线程的特点：
            //  1.是野线程：不受ThreadPool控制，也就是说没有最小最大线程之说，开线程速度也很快。
            //  2.是前台线程：主线程执行完要退出时，会等所有的前台线程执行完。直接Kill进程没有此限制。
            var thread1 = new Thread(new ThreadStart(Run));
            thread1.IsBackground = false; //默认是false即：前台线程
            thread1.Start();

            //线程池开线程
            ThreadPool.QueueUserWorkItem(x => Run());
        }


        /// <summary>
        /// 线程池ThreadPool的全局设置
        /// </summary>
        private static void RunThreadPoolGlobalSettingsDemo()
        {
            //线程不是你要多少就立即给你开多少的，有最小及最大线程数，小于最小你要多少就开多少，超过了大约每秒开2个，直到达到最大
            //默认 最小线程数==IO线程数==cpu核数,相当小，当然这个是可以设置的：
            int workerThreadsMin, completionPortThreadsMin, workerThreadsMax, completionPortThreadsMax;
            ThreadPool.GetMaxThreads(out workerThreadsMax, out completionPortThreadsMax);
            ThreadPool.GetMinThreads(out workerThreadsMin, out completionPortThreadsMin);
            var aaa1 = ThreadPool.SetMaxThreads(10000, 10000);
            var aaa = ThreadPool.SetMinThreads(1000, 1000); //设置大于最大，则返回失败。
            System.Net.ServicePointManager.DefaultConnectionLimit = int.MaxValue;
        }


        /// <summary>
        /// 常用的线程及并行操作，Task,Parallel,这些默认都是通过ThreadPool开线程的
        /// </summary>
        private static void RunTypical_Thread_Task_Parallel_Demo()
        {
            //启动个线程
            Task.Factory.StartNew(Run);
            Task.Factory.StartNew(() =>
            {
                Run();
            });


            //启动个线程
            Task.Run(() => Run());


            //并行for
            Parallel.For(0, 10, item => Console.WriteLine(item));
            Parallel.For(0, 10, item =>
            {
                Console.WriteLine(item);
            });


            //设置最大并行度
            Parallel.For(0, 10, new ParallelOptions { MaxDegreeOfParallelism = 2 }, item => Console.WriteLine(item));


            //并行foreach
            var lst = new List<int> { 1, 2, 3, 4, 5 };
            Parallel.ForEach(lst, item => Console.WriteLine(item));
            Parallel.ForEach(lst, Console.WriteLine);
            lst.AsParallel().ForAll(Console.WriteLine); //数组、集合都可以.AsParallel()

            //并行调用多个方法
            Parallel.Invoke(Run, Run, () => { Console.WriteLine(""); }, () => { });
        }

        /// <summary>
        /// 线程同步相当容易，就是个Task.WaitAll，当然还有更高级的，但基本这个就够用了。
        /// </summary>
        private static void RunThreadSyncDemo()
        {
            var sw = Stopwatch.StartNew();

            //获取酒店数量、列表
            var hotelCount = 0;
            var getHotels = Task.Run(() =>
            {
                Thread.Sleep(1234); //模拟查询酒店......
                hotelCount = 100;
            });

            //获取机票数量、列表
            var flightCount = 0;
            var getFlights = Task.Run(() =>
            {
                Thread.Sleep(2000); //模拟查询机票......
                flightCount = 10;
            });

            //等机票酒店都回来，什么？高大上的线程同步就是一句话？你以为呢？
            Task.WaitAll(getHotels, getFlights);

            //返回结果：
            Console.WriteLine($"查询耗时{sw.ElapsedMilliseconds}ms,酒店{hotelCount}个，机票{flightCount}个，可供你选择");
        }



        private static void Run()
        {
            Console.WriteLine($"ThreadId:{Thread.CurrentThread.ManagedThreadId}");
        }


        class LockTest
        {
            public static readonly object objLock = new object();
            /// <summary>
            /// 多线程要考虑 线程安全 及 锁，这个是锁的使用。线程安全相关的可以参考AboutCollection
            /// </summary>
            public static void LockDemo()
            {

                //演示：同步操作
                var addSync = 0;
                for (int i = 0; i < 10000; i++)
                {
                    addSync += i;
                }



                //演示：异步操作，没有使用lock时，最终的结果不对
                var addAsync = 0;
                Parallel.For(0, 10000, i => addAsync += i);
                var nolock = addSync == addAsync; //false



                //演示：异步操作，使用lock，最终结果正确
                var addAsyncWithLock = 0;
                Parallel.For(0, 10000, i =>
                {
                    lock (objLock)
                    {
                        addAsyncWithLock += i;
                    }
                });

                var withlock = addSync == addAsyncWithLock; //true
            }
        }


    }




}
