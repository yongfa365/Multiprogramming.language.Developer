using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;


namespace ConsoleApp.BestPractices
{
    ///<summary>
    ///官网地址
    ///    线程不安全的：https://docs.microsoft.com/en-us/dotnet/api/system.collections.generic
    ///    线程安全的：https://docs.microsoft.com/en-us/dotnet/api/system.collections.concurrent
    ///</summary>
    public class AboutCollection
    {
        public static void RunDemo()
        {
            //普通的集合都是非线程安全的，如果有多线程场景一定要使用线程安全的集合，否则可能出各种问题。
            //Linq使集合如虎添翼，Linq是懒加载的，所以在没有ToXXX或用之前，他只是表达式。

            RunListDemo();
            RunDictionaryDemo();
            RunHashSetDemo();
            RunQueueDemo();
            RunStackDemo();
        }
        private static void RunListDemo()
        {
            var lst1 = new List<string> { "1", "2", "3", "3" }; //["1","2","3","3"]
            lst1.AddRange(new string[] { "4", "5", "6" });

            var lst2 = new HashSet<string>(StringComparer.OrdinalIgnoreCase) { "A", "a", "b" }; //["A","b"] 一般不用

            var lst3 = new List<Person>
            {
                new Person{ Id=1,Name="A"},
                new Person{ Id=2,Name="B"},
                new Person{ Id=3,Name="B"},
            };

            var lst4 = new List<Person>();

            lst4.HasValue();//自己写的扩展方法
            lst4.IsNullOrEmpty();//自己写的扩展方法

            for (int i = 0; i < 100; i++)
            {
                lst4.Add(new Person { Id = i, Name = $"Name.{i}", Birthday = DateTime.Now.AddDays(i), Height = i / 3m });
            }

            var lst5 = lst4.Select(p => p.Id).ToList();
            var lst6 = lst4.ConvertAll(p => p.Id);
            var lst9 = lst4.First();
            var lst10 = lst4.FirstOrDefault(p => p.Id > 10);

            var lst7 = lst4
                .Where(p => p.Birthday > DateTime.Now.AddDays(30) && p.Id < 50) //筛选
                .OrderByDescending(p => p.Id) //排序
                .Skip(1) //跳过
                .Take(10) //获取
                .Select(p => new { p.Id, UserName = p.Name }) //转为匿名类
                .ToDictionary(p => p.Id, p => p.UserName) //转为字典
                ;

            var lst8 = lst4.Take(10).GroupBy(p =>
            {
                if (p.Height > 2)
                {
                    return "Height>2";
                }
                else
                {
                    return "Height<=2";
                }
            }).ToDictionary(p => p.Key, p => p.ToList());



            //线程安全的List
            var conlist = new ConcurrentBag<Person>(lst4);
            conlist.Add(new Person { });

        }


        private static void RunDictionaryDemo()
        {
            var dict1 = new Dictionary<int, string>
            {
                { 1, "111" },
                { 2, "222" },
                { 3, "333" },
            };

            var dict2 = new Dictionary<string, string>(StringComparer.OrdinalIgnoreCase)
            {
                { "A","111"},
                { "a","222"},
                { "b","333"},
            };

            //自定义比较器
            var dict3 = new Dictionary<Person, int>(new PredicateEqualityComparer<Person>((x, y) => x.Id == y.Id))
            {
                { new Person{ Id=1,Name="A"} ,1},
                { new Person{ Id=2,Name="B"} ,2},
                { new Person{ Id=1,Name="B" } ,3},
            };

            var dict = new Dictionary<string, int>();
            dict.Add("1", 2);//添加赋值
            dict["a"] = 1; //直接赋值
            dict.Add("2", 2);
            //dict3.Add("2", 2);//key重复会报错
            var dd = dict.TryAdd("2", 2);//不会报错，会返回false

            if (dict.ContainsKey("a"))
            {
                Console.WriteLine(dict["a"]);
            }

            if (dict.TryGetValue("2", out int getResult))
            {
                Console.WriteLine(getResult);
            }



            var keys=dict.Keys.ToList();
            var valus = dict.Values.ToList();


            //线程安全的
            var condict = new ConcurrentDictionary<int, Person>();
            condict.TryAdd(1, new Person { Id = 1, Height = 1 });
            condict.TryRemove(1,out Person resultTemp);
            condict.TryGetValue(1, out Person resultTemp2);


        }


        private static void RunHashSetDemo()
        {
            Console.WriteLine("\r\n★★★★★★HashSet<T>★★★★★★");

            var hs1 = new HashSet<string> { "1", "2", "3", "3" }; //["1","2","3"]

            var hs2 = new HashSet<string>(StringComparer.OrdinalIgnoreCase) { "A", "a", "b" }; //["A","b"]

            //自定义比较器
            var hs6 = new HashSet<Person>(new PredicateEqualityComparer<Person>((x, y) => x.Id == y.Id))
            {
                new Person{ Id=1,Name="A"},
                new Person{ Id=2,Name="B"},
                new Person{ Id=1,Name="B"},
            };

            var hs3 = new HashSet<int>();
            hs3.Add(1);
            hs3.Add(2);
            hs3.Add(3);

            var b1 = hs3.Contains(1); //true

            var hs4 = hs3.Where(p => p > 1).ToHashSet();

            //HashSet<T>没有线程安全的版本，可以用ConcurrentDictionary<T,T>代替
        }



        private static void RunQueueDemo()
        {
            var queue = new Queue<Person>();
            queue.Enqueue(new Person());
            queue.Enqueue(new Person());
            var count = queue.Count;

            queue.Dequeue();//没有值会报错
            queue.TryDequeue(out Person temp);

            //线程安全的队列：ConcurrentQueue<T>
            var conQueue = new ConcurrentQueue<Person>();
            conQueue.Enqueue(new Person());
            conQueue.Enqueue(new Person());
            conQueue.TryDequeue(out Person temp2);
        }


        private static void RunStackDemo()
        {
            //很少用，就不写了
            //var stack = new Stack<Person>();
            //stack.Push(new Person());
            //stack.TryPop(out Person temp);

        }

    }




}
