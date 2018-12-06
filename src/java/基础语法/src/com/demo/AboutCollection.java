package com.demo;

import com.demo.Entity.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//https://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html
//https://www.oracle.com/technetwork/articles/java/architect-streams-pt2-2227132.html
//https://blog.lahteenmaki.net/java-streams-vs-c-linq-vs-java6-updated.html
//Java的Lambda的的实现方法就是定义一堆接口，让你写。

public class AboutCollection {

    public static void RunDemo() {
        //jdk9对List,Set,Map都加了.of(),来生成只读的集合
        RunListDemo();
    }

    private static void RunListDemo() {
        //演示：初始化List或ArrayList的几种方法
        var lstInit01 = new ArrayList<String>();
        var lstInit02 = new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
        }};
        var lstInit03 = new ArrayList<Person>() {{
            add(new Person(1, "A"));
            add(new Person(2, "B"));
            add(new Person(2, "B"));
        }};

        //演示：添加元素的方法，单个的，List的，实现IEnumerable<T>接口的
        lstInit01.add("A");

        lstInit02.add("B");
        lstInit02.add("C");

        lstInit02.addAll(List.of("4", "5", "6")); //不能addAll数组，C#可以
        lstInit02.addAll(lstInit01);

        //以下几个生成不可修改的List,add,remove会报错
        var lstInitNCopy = Collections.nCopies(10, "ABC"); //ABC COPY10份，这是测试用的功能吧

        var lst_ReadOnly_1 = Arrays.asList(new String[]{"1", "2", "3", "3"});

        var lst_ReadOnly_2 = Arrays.asList("1", "2", "3", "3");

        var lst_ReadOnly_3 = List.of("1", "2", "3", "3"); //★推荐用这个，原因：短小精悍。

        //初始化后还想改，那就再包装下吧。
        var lstInit05 = new ArrayList(List.of("1", "2", "3", "3")); //★推荐
        lstInit05.add("4");

        //演示：lst的各种方法
        var lstData = new ArrayList<Person>();

        var lsthas = lstData != null && !lstData.isEmpty();//没有扩展方法自己写吧
        var lstEmpty = lstData == null || lstData.isEmpty();//自己写的扩展方法

        for (int i = 0; i < 100; i++) {
            var height = BigDecimal.valueOf(i).divide(new BigDecimal(3), 1, RoundingMode.HALF_EVEN);
            lstData.add(new Person(i, "Name" + i, LocalDateTime.now().plusDays(1), true, height, true));
        }


        var lst01 = lstData.stream().map(Person::getId).collect(Collectors.toList()); //将Id组成新的List
        var lst02 = lstData.stream().map(p -> p.getId()).collect(Collectors.toList()); //将Id组成新的List
        var lst03 = lstData.stream().max(Comparator.comparing(p -> p.getId())).get();
        var lst04 = lstData.stream().min(Comparator.comparing(p -> p.getId())).get();
        //没有findLast,可以用Google Guava's  Iterables.getLast(myIterable)，或者自己多写几行代码实现
        var lst05 = lstData.stream().findFirst().get();
        var lst06 = lstData.stream().filter(p -> p.getId() > 10).findFirst().get();
        var lst09 = lstData.stream().anyMatch(p -> p.getHuman());
        var lst10 = lstData.stream().allMatch(p -> p.getHuman());
        var lst11 = Stream.concat(lstData.stream(), lstData.stream()).collect(Collectors.toList());
        var lst12 = lstData.removeIf(p -> p.getId() > 80);

        var lst13 = lstData.stream()
                .filter(p -> p.getBirthday().isAfter(LocalDateTime.now().plusDays(30)) && p.getId() < 50)//筛选
                .sorted(Comparator.comparing(Person::getName).thenComparing(Person::getId).reversed()) //级联排序 https://www.concretepage.com/java/jdk-8/java-8-stream-sorted-example
                .skip(1) //跳过
                .limit(10) //获取
                .map(p -> new Person(p.getId(), p.getName())) //没有类似C#的匿名类，所以还是转为原生的吧
                .collect(Collectors.toMap(p -> p.getId(), p -> p)); //转为map


        var lst14 = lstData.stream().filter(distinctByKey(p -> p.getId())).collect(Collectors.toList());//Distinct的默认扩展不适合复杂类型，这个是自己写的方法

        //演示：GroupBy的更强大的实现
        var lst15 = lstData.stream().limit(10).collect(Collectors.groupingBy(p -> {
            //这里的内容就是构造Key的，最终返回的Key相同的会放到同一组
            if (p.getHeight().compareTo(BigDecimal.valueOf(2)) > 0) {
                return "Height>2";
            } else {
                return "Height<=2";
            }
        }));


        var lst16 = Collections.unmodifiableCollection(lstData); //变成只读集合

        //演示：线程安全的List的常用操作
        //TODO:应该用同步的，还是并行的？为什么没有并行List
        var wordList = Collections.synchronizedList(new ArrayList());

    }


    //TODO:为什么?不能用别的替代？t是怎么来的？
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
