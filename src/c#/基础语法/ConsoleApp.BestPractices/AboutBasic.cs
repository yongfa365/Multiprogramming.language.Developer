using System;
using System.Numerics;

namespace ConsoleApp.BestPractices
{
    //此文件时最基础功能的展示，所以都用的是具体类型，没有用var,而平时用基本都是var
    public class AboutBasic
    {
        public static void RunDemo()
        {
            #region 最基础类型
            {
                int intValue = 1;
                Int64 int64value = 2147483647000; //Int64别名long,Int32别名int
                long longValue = 1L;
                double doubleValue = 1.1;
                float floatValue = 1.1f;
                bool booltrue = true;
                bool boolfalse = false;
                char charValue = 'A';
                bool charisValue = 'A' > 26; //char是值类型的


                //不学java，10年了都不知道还有这么号人物
                BigInteger b_1 = BigInteger.Parse("123".Repeat(100));
                BigInteger b_2 = BigInteger.Divide(b_1, 3);
                BigInteger b_3 = b_1 / 3;

                //decimal对应java里的BigDecimal，并且有重载运算符，所以更方便
                decimal decimalValue = 1.1m;

                //0.099999999999999978  double 精度不够
                double double_bad = 1.0 - 0.9;

                //decimal精度够，企业级应用都用这个，而不用float,double
                decimal decimal_good = 1.0m - 0.9m;

                //只要一个有m标识，整个就是decimal 的,当然也可以都加上
                decimal decimal_all = (1 + 1 - 1) * 1 / 3m;

                bool decimal_compare = 1m > 3m;

                //生成一个GUID,如："7e0d3fc3-5447-4cf2-accd-366e3ade0973"
                Guid guid = Guid.NewGuid();
                Guid guid2 = Guid.Parse("7e0d3fc3-5447-4cf2-accd-366e3ade0973");

                //获取随机数
                int rnd = new Random().Next(0, 10000);

                //数组的各种操作跟集合差不多，有集合后就很少用数组了
                string[] strs = new string[10];
                string[] strs2 = { "A", "B" };
                string[] strs3 = new string[] { "A", "B" };
                string[] strs4 = new[] { "A", "B" };
                string str5 = strs4[0];

                //三元运算符
                string isTrue = intValue == 2 ? "True" : "False";
            }
            #endregion


            #region 可空类型的用法， T? ??
            {
                Nullable<int> a123 = null; //写法麻烦，一般不用
                int? a1 = null;
                int? a11 = null;
                if (a1.HasValue)
                {
                    var a2 = a1.Value;
                }
                var a3 = a1 ?? a11 ?? 0;//返回第一个不为null的或者返回null
                var p = new Person();
                if (p?.Birthday?.Date == DateTime.Now.Date)
                {
                    //if里的语句原生写法如下，很长。
                    var isSame = p != null && p.Birthday != null && p.Birthday.Value.Date == DateTime.Now.Date;
                }
            }
            #endregion


            #region 元组 Tuple<T1,T2,T3...T8> ,AnonymousType,Lambda表达式，Action<T1,T2..T8>, Func<T1,T2.T8..Tout> dynamic
            {
                //Tuple ，初始化后，属性不能改，是ReadOnly的
                var t1 = new Tuple<string, int, DateTime>("1", 1, DateTime.Now);
                var t_1 = t1.Item1 + t1.Item2 + t1.Item3;
                //t1.Item1 = ""; //编译报错，不能改

                var t2 = Tuple.Create("1", 1, DateTime.Now);

                var t3 = ("1", 1, DateTime.Now);


                //本地变量
                (string FirstName, string LastName) t4 = ("永法", "柳");
                var t_4 = t4.FirstName;
                t4.FirstName = "柳";//可以改。

                var t5 = (FirstName: "永法", LastName: "柳");
                var t_5 = t5.FirstName;

                //虽然Java与C#都有匿名类，但完全不同，Java的匿名类是“实现方法的”，C#是“构造属性的”。
                //匿名类，初始化后，属性不能改，是ReadOnly的。
                var ta3 = new { A = "1", B = 1, C = DateTime.Now, E = true, F = new Person() };

                var actiontestInt = 1;
                Helper.NoErrorInvoke(() =>
                {
                    var x = "随便写" + actiontestInt;
                    actiontestInt++;//可以改变外面变量的值，都不用非要返回什么的
                });

                Func<int, int, int> add = (x, y) => x + y;
                Func<int, int, int> add2 = (x, y) =>
                {
                    if (x > y)
                    {
                        return x + y;
                    }
                    return x - y;
                };

                var addresult = add(1, 2);


                //dynamic 的更多强大之处及他使类型变弱后带来的问题，就自己去体验吧。
                dynamic dd = DateTime.Now;
                var dd_1 = dd.Year; //动态类型的，所以.Year不会有智能提示，要手敲出来

                dd = "123";
                var dd_2 = dd[2].ToString();//动态类型的，所以[2]及ToString()不会有智能提示，要手敲出来.
            }
            #endregion


            #region if else do while for foreach switch
            {
                if (1 + 1 == 2)
                {
                    //code
                }
                else if (2 > 1)
                {
                    //code
                }
                else
                {
                    //code
                }

                var i = 10;
                while (i-- > 0)
                {
                    //code
                }

                do
                {
                    //code
                } while (i++ > 10);


                for (int j = 0; j < 10; j++)
                {
                    //code
                }

                var items = new int[] { 1, 2, 3 };
                foreach (var item in items)
                {
                    Console.WriteLine(item);
                }

                var ptype = ProductType.Flight;
                var ptypes = ProductType.Flight|ProductType.Bus|ProductType.Hotel;
                var ptypeHasFlag = ptype.HasFlag(ProductType.Flight); //true

                //输入switch后按tab,再输入ptype，再按enter，就自动生成一下内容了
                switch (ptype)
                {
                    case ProductType.Default:
                        break;
                    case ProductType.Hotel:
                        break;
                    case ProductType.Flight:
                        break;
                    case ProductType.Bus:
                        break;
                    case ProductType.FlightHotel:
                        break;
                    case ProductType.BusHotel:
                        break;
                    default:
                        break;
                }
            }
            #endregion


            #region 数学方法 因业务系统只用decimail所以只关注decimail的，其他的也一样的用法
            {

                var du = Math.Ceiling(123.456m); //天花板
                var dd = Math.Floor(123.456m); //地板

                // 四舍五入
                var dd1 = Math.Round(1.44m, 1, MidpointRounding.AwayFromZero);
                var dd2 = Math.Round(1.45m, 1, MidpointRounding.AwayFromZero);

                // 四舍六入五成双,了解下就行
                var d1 = Math.Round(1.44m, 1);//1.4  被修约的数字<=4时，该数字舍去
                var d2 = Math.Round(1.45m, 1);//1.4  被修约的数字等于5时，要看5前面的数字，若是奇数则进位，若是偶数则将5舍掉，即修约后末尾数字都成为偶数
                var d4 = Math.Round(1.451m, 1);//1.5 被修约的数字等于5时，若5的后面还有不为“0”的任何数，则此时无论5的前面是奇数还是偶数，均应进位。
                var d3 = Math.Round(1.75m, 1);//1.8  被修约的数字等于5时，要看5前面的数字，若是奇数则进位，若是偶数则将5舍掉，即修约后末尾数字都成为偶数
                var d6 = Math.Round(1.46m, 1);//1.5  被修约的数字>=6时，则进位
            }
            #endregion

            InfinateParam(110, 120, 911);
            InfinateParam(new []{ 111, 222, 333 });
        }

        public static void InfinateParam(params int[] args)
        {
            foreach (var item in args)
            {
                Console.WriteLine(item);
            }
        }




    }


    class DynamicDemoClass
    {
        static dynamic field;
        dynamic prop { get; set; }

        public dynamic exampleMethod(dynamic d)
        {
            dynamic local = "Local variable";
            int two = 2;

            if (d is int)
            {
                return local;
            }
            else
            {
                return two;
            }
        }
    }
}
