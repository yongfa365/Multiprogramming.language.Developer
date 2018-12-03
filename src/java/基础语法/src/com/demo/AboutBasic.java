package com.demo;

import com.demo.Entity.*;
import com.demo.Helper.Helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;


public class AboutBasic {
    public static void RunDemo() {


        //region 最基础类型
        {
            var a = 1; //int
            var b = 1.1; //double
            var f = 1.1f; //float

            //var m = 1.1m; //java没有decimal，有BigDecimal,BigInteger,但是没有重载操作符，所以+-*/%都没法用在BigDecimal上。效率低，奈它何
            var m = new BigDecimal("987654321".repeat(1000));
            var m_1 = m.divide(new BigDecimal("3")); //c#：1m+1在java得写成：            new BigDecimal(1).add(new BigDecimal(1));
            var double_bad = 1.0 - 9 * 0.1; //0.09999999999999998  double 精度不够，一般不要用，
            var decimal_good = new BigDecimal("1.0").subtract(BigDecimal.valueOf(9 * 0.1)); //0.1 decimal 精度可以
            var decimal_all = new BigDecimal("1")
                    .add(new BigDecimal("1"))
                    .subtract(new BigDecimal("1"))
                    .multiply(new BigDecimal("1"))
                    .divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP); //除不尽会报错，所以要设置保留位数，C#的decimal不会

            var l = 1L; //long
            var d = true;
            var e = false;
            var c = 'A';
            var bb = 'A' > 26; //char是值类型的

            var guid = UUID.randomUUID(); //"7e0d3fc3-5447-4cf2-accd-366e3ade0973"

            var rnd = new Random().nextInt(10000); //获取随机数

            var strs = new String[10];
            String[] strs2 = {"A", "B"};

            var isTrue = a == 2 ? "True" : "False";
        }
        //endregion

        //Java没有值类型之说，都是引用类型的，所以可以直接赋值null，所以没有C#里的可空类型：int? ,Nullable<int> ，
        {
            Integer int1 = null;
            if (int1 == null) int1 = 123;


            ZonedDateTime time1 = null;
            if (time1 == null) time1 = ZonedDateTime.now();
            System.out.println("断点用");
        }

        //region 元组 Tuple<T1,T2,T3...T8> ,AnonymousType,Lambda表达式，Action<T1,T2..T8>, Func<T1,T2.T8..Tout> dynamic
        {
            //java 没有Tuple,但有多重实现方法：https://stackoverflow.com/a/25615100/1879111 其中我感觉最好的还是lombok的方案

            //java 没有Action<T>，Func<T>但有一堆别的：DoubleFunction,Supplier<T>,Consumer<T>   貌似都是用在lambda里的
            var int1 = 1;
            var str1 = "1";
            var person = new Person2();
            Helper.NoErrorInvoke(item ->
            {
                person.Id = 1;//可以用,可以改
                person.Name = "11111";//可以用,可以改
                var x = "随便写" + int1 + str1;
                // str1+="1";//可以用,但不能改,跟C#不同
                //int1++;//可以用,但不能改,跟C#不同
            });
            var p = person.Id;

            Function<Integer, String> func = x -> x + "1";
            var func_1 = func.apply(10);


            //java没有 dynamic类型

        }
        //endregion


        //region if else do while for foreach switch
        {
            if (1 + 1 == 2) {
                //code
            } else if (2 > 1) {
                //code
            } else {
                //code
            }

            var i = 10;
            while (i-- > 0) {
                //code
            }

            do {
                //code
            } while (i++ > 10);


            for (int j = 0; j < 10; j++) {
                //code
            }

            var items = new int[]{1, 2, 3};
            for (var item : items) //没有foreach,就这个了
            {
                System.out.println(item);
            }

            //TODO:Enum还没学。
            var str = "a";
            switch (str) {
                case "a":
                case "b":
                    System.out.println("a");
                    break;
                case "c":
                    System.out.println("c");
                    break;
                default:
                    break;
            }


        }
        //endregion


        //region 数学方法 因业务系统只用BigDecimal所以只关注BigDecimal的,其他类型也可以转成BigDecimal用，直接用默认的Math.xxx不好用，不推荐
        {
            var du = new BigDecimal("123.456").setScale(0, RoundingMode.UP); //天花板
            var dd = new BigDecimal("123.456").setScale(0, RoundingMode.DOWN); //地板

            var d4 = Math.ceil(123.456d);
            var d5 = Math.floor(123.456d);

            // 四舍五入
            var dd1 = new BigDecimal("1.44").setScale(1, RoundingMode.HALF_UP);
            var dd2 = new BigDecimal("1.45").setScale(1, RoundingMode.HALF_UP);

            // 四舍六入五成双,了解下就行
            var d1 = new BigDecimal("1.45").setScale(1, RoundingMode.HALF_EVEN);
            var d2 = new BigDecimal("1.451").setScale(1, RoundingMode.HALF_EVEN);
            var d3 = new BigDecimal("1.75").setScale(1, RoundingMode.HALF_EVEN);

        }
        //endregion
    }


}




