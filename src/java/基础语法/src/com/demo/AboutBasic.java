package com.demo;

import com.demo.Entity.EnumType.ProductType;
import com.demo.Entity.Person2;
import com.demo.Helper.Helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

//此文件时最基础功能的展示，所以都用的是具体类型，没有用var,而平时用基本都是var
public class AboutBasic {
    public static void main(String[] args) {
        //region 最最基础类型
        {
            //8个原始类型(Primitive Type )，这些是不能为null的，其他的都能为null。与C#不同，C#是int与Int32等效的
            //https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
            byte byteValue = 127;
            short shortValue = 32767;
            int intValue = 100000;
            long longValue = 1000000L;
            float floatValue = 1.1f;
            double doubleValue = 1.1;
            boolean boolValue = true;
            char charValue = 'A';
        }
        //endregion

        //region 最基础类型
        {
            Integer intValue = 1;
            Long longValue = 1L;
            Double doubleValue = 1.1;
            Float floatValue = 1.1f;
            Boolean booltrue = true;
            Boolean boolfalse = false;
            Character charValue = 'A';
            Boolean charisValue = 'A' > 26; //char是值类型的


            //测试时重复了1万次，运行很快，但debug时导致idea很慢因为要toString();细节：https://intellij-support.jetbrains.com/hc/en-us/articles/206544799-Java-slow-performance-or-hangups-when-starting-debugger-and-stepping
            BigInteger bigintValue = new BigInteger("123".repeat(100));

            //java没有decimal，有BigDecimal,BigInteger,但是没有重载操作符，所以+-*/%都没法用在BigDecimal上。效率低，奈它何
            BigDecimal bigDecimalValue = new BigDecimal("987654321");

            //c#：1m+1m在java得写成： new BigDecimal(1).add(new BigDecimal(1));
            BigDecimal m_1 = BigDecimal.ZERO.divide(new BigDecimal("3"));

            //0.09999999999999998  double 精度不够，一般不要用，
            Double double_bad = 1.0 - 9 * 0.1;

            //BigDecimal精度够，企业级应用都用这个，而不用float,double
            //BigDecimal初始化保证精度的两种方法：new BigDecimal("0.9")   或 BigDecimal.valueOf(0.9)
            // 不能这样初始化：new BigDecimal(0.9)，不信在jshell里试下
            BigDecimal decimal_good = new BigDecimal("1.0").subtract(BigDecimal.valueOf(0.9));

            BigDecimal decimal_all = new BigDecimal("1")
                    .add(new BigDecimal("1"))
                    .subtract(new BigDecimal("1"))
                    .multiply(new BigDecimal("1"))
                    //除不尽会报错，所以要设置保留位数，C#的decimal不会报错
                    .divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);

            //其实就类似：前面的-后面的，结果>0说明前面的大
            boolean decimal_compare = new BigDecimal("1").compareTo(BigDecimal.valueOf(3)) > 0;

            //生成一个GUID,如："7e0d3fc3-5447-4cf2-accd-366e3ade0973"
            UUID guid = UUID.randomUUID();
            UUID guid2 = UUID.fromString("7e0d3fc3-5447-4cf2-accd-366e3ade0973");

            //获取随机数
            int rnd = new Random().nextInt(10000);
            int rnd2 = ThreadLocalRandom.current().nextInt(10000);

            String[] strs = new String[10];
            String[] strs2 = {"A", "B"};
            String[] strs3 = new String[]{"A", "B"};
            //var strs4 = new [] { "A", "B" }; 不支持这种写法
            String str5 = strs3[0];

            //三元运算符
            String isTrue = intValue == 2 ? "True" : "False";
        }
        //endregion

        //Java没有值类型之说，都是引用类型的，所以可以直接赋值null，所以没有C#里的可空类型：int? ,Nullable<int> ，
        {
            Integer int1 = null;

            var a3 = Optional.ofNullable(int1).orElse(123);

            if (int1 == null) {
                int1 = 123;
            }


            ZonedDateTime time1 = null;
            if (time1 == null) {
                time1 = ZonedDateTime.now();
            }
            System.out.println("断点用");
        }

        //region 元组 Tuple<T1,T2,T3...T8> ,AnonymousType,Lambda表达式，Action<T1,T2..T8>, Func<T1,T2.T8..Tout> dynamic
        {
            //java 没有Tuple,但有多重实现方法：https://stackoverflow.com/a/25615100/1879111 其中我感觉最好的还是lombok的方案

            //java 没有Action<T>，Func<T>但有一堆别的类似的FunctionalInterface：Predicate<T>,Supplier<T>,Consumer<T>
            int int1 = 1;
            String str1 = "1";
            Person2 person = new Person2();
            Helper.NoErrorInvoke(() ->
            {
                person.Id = 1;//实体里的对象或者类的字段，可以用,可以改
                person.Name = "11111";
                String x = "随便写" + int1 + str1;
                // str1+="1";//上下文里的变量，可以用,但不能改,跟C#不同
                //int1++;
            });
            int p = person.Id;

            //java的func.apply(arg) == C#的func(arg)，至于apply,test,accept其实没什么意义只是需要个名字而已
            Function<Integer, String> func = x -> x + "1";
            String func_1 = func.apply(10);


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

            int i = 10;
            while (i-- > 0) {
                //code
            }

            do {
                //code
            } while (i++ > 10);


            for (int j = 0; j < 10; j++) {
                //code
            }

            int[] items = new int[]{1, 2, 3};
            for (var item : items) //这就是所谓的foreach，同时多数集合自带.forEach()
            {
                System.out.println(item);
            }


            //Java的Enum跟其他语言如C#不同。也没有运算符重载如:逻辑或“|”,所以这些逻辑操作不方便，也没有HasFlag()，可以用Set变相实现。
            var ptypes = Set.of(ProductType.FLIGHT, ProductType.BUS, ProductType.HOTEL);

            var hasFlag = ptypes.contains(ProductType.FLIGHT);

            var ptype = ProductType.FLIGHT;
            //不能像C#的快捷键一样快速生成case
            switch (ptype) {
                case DEFAULT:
                    break;
                case HOTEL:
                    break;
                default:
                    break;
            }


        }
        //endregion


        //region 数学方法 因业务系统只用BigDecimal所以只关注BigDecimal的,其他类型也可以转成BigDecimal用，直接用默认的Math.xxx不好用，不推荐
        {
            BigDecimal du = new BigDecimal("123.456").setScale(0, RoundingMode.UP); //天花板
            BigDecimal dd = new BigDecimal("123.456").setScale(0, RoundingMode.DOWN); //地板

            // 不能设置保留几位小数，需要转成BigDecimal才行
            Double d4 = Math.ceil(123.456d);
            Double d5 = Math.floor(123.456d);

            // 四舍五入
            BigDecimal dd1 = new BigDecimal("1.44").setScale(1, RoundingMode.HALF_UP);
            BigDecimal dd2 = new BigDecimal("1.45").setScale(1, RoundingMode.HALF_UP);

            // 四舍六入五成双,了解下就行
            BigDecimal d1 = new BigDecimal("1.45").setScale(1, RoundingMode.HALF_EVEN);
            BigDecimal d2 = new BigDecimal("1.451").setScale(1, RoundingMode.HALF_EVEN);
            BigDecimal d3 = new BigDecimal("1.75").setScale(1, RoundingMode.HALF_EVEN);

        }
        //endregion


        infinateParam(110, 120, 911);
        infinateParam(new Integer[]{111, 222, 333});
    }

    public static void infinateParam(Integer... args) {
        for (var item : args) {
            System.out.println(item);
        }
    }


    public static void noThrowsExceptionInMethod() {
        Helper.NoErrorInvoke(() -> {throw new Exception();});
        try {
            //这种事 【checked异常】，需要try{}catch{}处理或在方法上throws Exception抛出
            throw new Exception("我是checked异常");
        } catch (Exception ex) {
            //如果不想处理【checked异常】，想让框架处理，那可以包装成【Runtime异常】这样就不用在方法一级写throws 了
            throw new RuntimeException(ex);
        }
    }

}




