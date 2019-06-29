package com.demo;

import com.demo.Entity.EnumType.ColorType;
import com.demo.Entity.Person;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;


//https://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html
//https://www.oracle.com/technetwork/articles/java/architect-streams-pt2-2227132.html
//https://blog.lahteenmaki.net/java-streams-vs-c-linq-vs-java6-updated.html
//Java的Lambda用的是函数接口。C#的Lambda用的是用委托

public class AboutCollection {
    public static void main(String[] args) throws Exception {
        //jdk9对80多个类增加了200多个.of(),来生成不可变的对象。
        RunStreamDemo();

        RunOthersDemo();
    }


    private static void RunStreamDemo() {
        //C#.Linq与Java.Stream不同。Java有必要单独介绍下Stream()

        //演示：Stream初始化的几种方法
        Stream<Integer> init01 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Stream<Integer> init02 = Stream.of(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        Stream<String> init03 = Stream.of("A", "B", "C");
        Stream<String> init04 = Stream.of("DEF".split(""));
        Stream<Integer> init05 = List.of(1, 2, 3).stream();
        Stream<LocalTime> init06 = Stream.generate(() -> LocalTime.now()); //得到一个无限循环的
        Stream<Integer> init07 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

        //演示：IntStream, LongStream, DoubleStream的一些常用方法
        // 创建包含基本类型1， 2， 3的 IntStream
        IntStream intStream = IntStream.of(1, 2, 3);

        // 创建一个包含1到9的 IntStream，后边不闭合
        IntStream range = IntStream.range(1, 10);

        // 创建一个包含1到10的 IntStream
        IntStream rangeClosed = IntStream.rangeClosed(1, 10);

        // 创建一个包含3的 IntStream
        IntStream generated = IntStream.generate(() -> 3);

        // 得到一个无限循环的 IntStream, 值为 1, 3, 5, 7 ...
        IntStream infinite = IntStream.iterate(1, p -> p + 2);

        // 得到一个有限循环的 IntStream, 值为 1, 3, 5, 7 9
        IntStream finite = IntStream.iterate(1, p -> p < 10, p -> p + 2);

        //随机10个
        IntStream randomint01 = new Random().ints(10);

        //1-100之间，无限循环的IntStream
        IntStream randomint02 = new Random().ints(1, 100);

        //1-100之间，随机10个
        IntStream randomint03 = new Random().ints(10, 1, 100);

        //基类型int,bool,float,long等需要.boxed()下才能转成泛型的
        Stream<Integer> boxed = IntStream.of(1, 2, 3).boxed();


        //演示：转换为List或Array,map等
        var toarray01 = IntStream.of(1, 2, 3).toArray();
        var toarray02 = Stream.of(1, 2, 3, 4).toArray(Integer[]::new);

        var tolist01 = Stream.of(1, 2, 3, 4, 5, 6).filter(p -> p % 2 == 0).collect(Collectors.toList());
        var tolist02 = Stream.of(1, 2, 3, 4, 5, 6).collect(Collectors.toList());

        var tolist03 = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        var tolist04 = IntStream.of(1, 2, 3).mapToObj(String::valueOf).collect(Collectors.toList());


        var map01 = Stream.of("A", "B", "C").map(String::toLowerCase);
        var map02 = Stream.of("A", "B", "C").map(p -> new Person(1, p.repeat(10))); //转换成新对象


        //★stream只能用一次，当终结后就不能再用了，如：sum,collect。C#的Linq产生的中间物IEnumerable是可以重用的。
        var tempStream = Stream.of("A", "B", "C");
        var tempStream1 = tempStream.max(Comparator.comparing(p -> p));
        //var tempStream2 = tempStream.min(Comparator.comparing(p -> p)); //stream只能用一次，再次使用会报错，与C#不同

        //演示：并行

        //串行，正常顺序
        LongStream.range(1, 10).forEach(System.out::println);

        //并行，乱序的，与串行写法类似，就加个.parallel()跟C#一样好用
        LongStream.range(1, 10).parallel().forEach(System.out::println);

        //其他操作就放到List里说了
    }



    private static void RunOthersDemo() {
        getList().forEach(System.out::println);
    }

    private static List<Color> getList() {
        if (123 == 213) {
            return List.of(Color.BLACK, Color.GREEN);
        } else {
            //如果返回为null则调用方还要判断,jdk自带了几个immutable的默认的空集合,Collections.EMPTY_MAP;Collections.EMPTY_SET
            return Collections.EMPTY_LIST;
        }
    }
}


class ComparatorHelper {
    public static <T> Comparator<T> bool(BiPredicate<T, T> func) {
        return (x, y) -> func.test(x, y) ? 0 : 1;
    }

    //泛型类型限定符，上限：? extends T  下限：? supper T
    //https://stackoverflow.com/a/27872852/1879111
    //https://howtodoinjava.com/java8/stream-distinct-by-multiple-fields/
    public static <T> Predicate<T> distinct(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        //最终有用的就是这个lambda,所以这里的seen其实是通用的，而不是每次都会new
        return item -> seen.add(keyExtractor.apply(item));
    }

}




