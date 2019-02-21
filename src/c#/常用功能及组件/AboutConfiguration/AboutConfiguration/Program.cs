using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using AboutConfiguration.CustomConfigurationProvider;
using AboutConfiguration.EFConfigurationProvider;
using AboutConfiguration.Entity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;

namespace AboutConfiguration
{
    //这个Demo花了3天
    class Program
    {
        static void Main(string[] args)
        {
            //官方介绍：https://docs.microsoft.com/en-us/aspnet/core/fundamentals/configuration/
            //专家解析：https://www.paraesthesia.com/archive/2018/06/20/microsoft-extensions-configuration-deep-dive/
            //柳永法解析：
            //  可以很方便的从xml,ini,json读取配置，可以一次性加载多个配置，配置是分层的，相同的Key会被最后添加的覆盖掉。
            //  最终用Key:SubKey的作为Key将数据扁平化，Key不区分大小写。
            //  可以将有多级结构的json绑定到对应的实体，是用反射实现的，类似反序列化的功能str.FromJson<T>()。


            //以下Demo可能有异步操作，可以通过 注释掉不用的 只留要看的，来测试

            //★大而全的Demo
            AllInOneDemo(args);

            //★分层Demo演示
            HierarchicalDemo();

            //★自定义Provider,可以从db,api等位置加载配置，及间隔多久自动更新
            CustomProviderDemo();

            //reload的问题演示
            FileAutoReloadDemo();

            //★BindToObject后reload时要更新Object
            ToObjectThenReloadThenReBuildDemo();

            Console.ReadLine();

        }

        private static void AllInOneDemo(string[] args)
        {
            //Key忽略大小写
            //合并后的结果的官方的测试用例：https://github.com/aspnet/Extensions/blob/master/src/Configuration/Config/test/ConfigurationTest.cs


            var allSettings = new ConfigurationBuilder()
                //设置配置的根目录，不会改变系统的其他目录，必须是绝对路径
                .SetBasePath(Path.Combine(Directory.GetCurrentDirectory(), "Configs"))

                //以下这一堆.Add*都是扩展方法实现的provider，有Microsoft写的，也有自定的
                //Add***File相关的，都自带reloadOnChange设置，为true则会自动加载
                //命令行没办法reload，环境变量一般也不会变，所以没有reload功能


                //这些provider是分层的，相同Key的值会被后添加覆盖掉。内部其实还是分层的并没有改变。
                //官方测试用例：https://github.com/aspnet/Extensions/blob/master/src/Configuration/Config.Json/test/JsonConfigurationTest.cs
                .AddJsonFile("logsettings.json", optional: false, reloadOnChange: true)
                .AddJsonFile("basicsettings.json", optional: false, reloadOnChange: true)

                //ini比较老的格式，如非必要，不建议使用
                //官方测试用例：https://github.com/aspnet/Extensions/blob/master/src/Configuration/Config.Ini/test/IniConfigurationTest.cs
                .AddIniFile("logsettings.ini", optional: false, reloadOnChange: true)

                //xml比较老的格式，支持不全，如非必要，不建议使用
                //xml的不支持list结构的，如之前的web.config里的appSettings那种结构：<add key="" value="" />
                //官方测试用例：https://github.com/aspnet/Extensions/blob/master/src/Configuration/Config.Xml/test/XmlConfigurationTest.cs
                .AddXmlFile("logsettings.xml", optional: false, reloadOnChange: true)
                .AddXmlFile("tvshow.xml", optional: false, reloadOnChange: true)

                //命令行参数，可以在Project.Properties.Debug.Application arguments里模拟，或者自行命令行带上
                //官方测试用例：https://github.com/aspnet/Extensions/blob/master/src/Configuration/Config.CommandLine/test/CommandLineTest.cs
                .AddCommandLine(args)

                //环境变量，可以在Project.Properties.Debug.Environment variables里模拟，或者自行在操作系统里设置
                //官方测试用例：https://github.com/aspnet/Extensions/blob/master/src/Configuration/Config.EnvironmentVariables/test/EnvironmentVariablesTest.cs
                .AddEnvironmentVariables() //可以指定prefix

                //如果有其他数据源的，都可以用这种方式实现，调用API后赋值或内存集合
                .AddInMemoryCollection(dict1)

                //这个是官方给的一个Demo，使用EF内存DB，还是很简单明了的，没有reload功能
                .AddEFConfiguration(options => options.UseInMemoryDatabase("InMemoryDb"))
                .Build();


            #region 使用场景一：根据Key取Value

            //xxx["key"]这种返回类型为string。当key不存在时或者key下面有Children时则返回null
            var noExistThen_null = allSettings["XXXXXXXXXXXXXXXXXXXXXXXX"];
            var hasChildrenThen_null = allSettings["tvshow:metadata"];
            var title = allSettings["tvshow:metadata:title"];
            var hotelconn = allSettings["ConnectionStrings:HotelDB"];
            var flightconn = allSettings.GetConnectionString("HotelDB");
            var logconn = allSettings["ConnECTIONStrings:Log.RabbitMQ.ErRORLOG"];

            //可以指定返回类型，内部实现是：将Value从string转为T，能转则转否则返回null，如："True"->true,"123"->123
            var tobool = allSettings.GetValue<bool>("logging:enabled");
            var tourl = allSettings.GetValue<Uri>("tvshow:url");

            #endregion



            #region 使用场景二：BindToObject

            //ICollection.GetSection("")不会返回null，所以要判断是否存在得用.Exists()
            var sectionExists1 = allSettings.GetSection("ConnectionStrings").Exists();
            var sectionExists2 = allSettings.GetSection("ConnectionStringsXXXXXXX").Exists();

            //Get每次都是获取一个新的对象
            var allConn = allSettings.GetSection("ConnectionStrings").Get<ConnectionStrings>();
            var allConnHashCode1 = allConn.GetHashCode();
            allConn = allSettings.GetSection("ConnectionStrings").Get<ConnectionStrings>();
            var allConnHashCode2 = allConn.GetHashCode();

            //Bind每次对象都是同一个，会逐个属性比较赋值，传入为null时不赋值
            allSettings.GetSection("ConnectionStrings").Bind(allConn);
            var allConnHashCode11 = allConn.GetHashCode();
            allSettings.GetSection("ConnectionStrings").Bind(allConn);
            var allConnHashCode22 = allConn.GetHashCode();

            //支持层次嵌套绑定对象
            var list = new List<Person>();
            allSettings.GetSection("Persons").Bind(list);

            #endregion



            #region 使用场景三：遍历Key:Value

            //GetChildren只会处理当前级别，碰到还有下级的会返回null，而不会下钻
            var items = allSettings.GetSection("ConnectionStrings").GetChildren();
            foreach (var item in items)
            {
                //这里Path才是真正的Key，带:的。Key只是当前的而已，没包括上级的。
                Console.WriteLine($"Path:{item.Path}\t Key:{item.Key}\t Value:{item.Value}");
            }

            Console.WriteLine();

            //一般不这么用，仅演示
            foreach (var item in allSettings.AsEnumerable())
            {
                Console.WriteLine($"Key:{item.Key.PadRight(50)}Value:{item.Value}");
            }

            #endregion
        }


        /// <summary>
        /// FileProvider可以reloadOnChange,但仅仅是reload成键值对。
        /// 如果Build后有赋值给别的变量，reload后那些变量的值并不会跟着变。
        /// 所以要么每次都从键值对里拿如xxx[key],要么自己ChangeToken.OnChange
        /// </summary>
        private static void FileAutoReloadDemo()
        {
            var allSettings = new ConfigurationBuilder()
                .AddJsonFile("Configs\\basicsettings.json", optional: true, reloadOnChange: true)
                .Build();

            var hotelconn1 = allSettings["ConnectionStrings:HotelDB"];

            var persons1 = new List<Person>();
            allSettings.GetSection("Persons").Bind(persons1);

            Task.Run(() =>
            {
                while (true)
                {
                    var hotelconn2 = allSettings["ConnectionStrings:HotelDB"];

                    var persons2 = new List<Person>();
                    allSettings.GetSection("Persons").Bind(persons2);

                    Console.WriteLine("FileAutoReloadDemo.Start");

                    Console.WriteLine($"hotelconn1:{hotelconn1}");
                    Console.WriteLine($"hotelconn2:{hotelconn2}");

                    Console.WriteLine($"persons1:{string.Join("|", persons1)}");
                    Console.WriteLine($"persons2:{string.Join("|", persons2)}");

                    Console.WriteLine("FileAutoReloadDemo.End");
                    Thread.Sleep(3000);
                }
            });
        }

        private static void ToObjectThenReloadThenReBuildDemo()
        {

            Task.Run(() =>
            {
                while (true)
                {
                    Console.WriteLine("\r\nToObjectThenReloadThenReBuildDemo.Start");

                    Console.WriteLine($"HotelDBConn:{ConfigurationManager.ConnectionStrings.HotelDB}");
                    Console.WriteLine($"Persons:{string.Join("|", ConfigurationManager.Persons)}");

                    Console.WriteLine("ToObjectThenReloadThenReBuildDemo.End\r\n");
                    Thread.Sleep(3000);
                }
            });
        }

        private static void HierarchicalDemo()
        {
            //添加两个数据源，预期结果是：dict2的内容会替换dict1的
            var settings = new ConfigurationBuilder()
                .AddInMemoryCollection(dict1)
                .AddInMemoryCollection(dict2)
                .Build();

            //这里可以看到value已经替换成dict2的值了
            var avalue = settings["a"];
            var bvalue = settings["B"];

            //这里会输出4个KeyValue,貌似dict1的内容被改了，同时dictionary不是忽略大小写的吗，为什么有4个而不是2个？
            //具体Debug时看下settings.Providers[x].Data就知道了，各provider内容不变，只是最终呈现是叠加后的结果。
            //Key: b Value:B2
            //Key:B Value:B2
            //Key:a Value:A2
            //Key:A Value:A2
            foreach (var item in settings.AsEnumerable())
            {
                Console.WriteLine($"Key:{item.Key}\tValue:{item.Value}");
            }

            //Update也会针对各个provider里的key都改掉，可以debug看下
            settings["a"] = "11111111111111";

            //Add也会针对各个provider添加
            settings["abc"] = "2222222222";

        }

        private static void CustomProviderDemo()
        {
            var dict = new Dictionary<string, string>();
            var settings = new ConfigurationBuilder()
                .AddCustomGuidConfiguration()
                .AddCustomDateTimeConfiguration()
                .AddApiConfiguration()
                .Build();



            Task.Run(() =>
            {
                while (true)
                {
                    var keys = string.Join("|", settings.GetChildren().Select(p => p.Key));
                    Console.WriteLine("CustomProviderDemo:" + keys);
                    Thread.Sleep(1000);
                }
            });

        }

        private static readonly Dictionary<string, string> dict1 = new Dictionary<string, string>
        {
            { "a", "a1" },
            { "b", "b1" },
        };

        private static readonly Dictionary<string, string> dict2 = new Dictionary<string, string>
        {
            { "A", "A2" },
            { "B", "B2" },
        };
    }
}
