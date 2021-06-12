package com.demo;


import com.demo.Entity.Person;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AboutCollection_List {
    public static void main(String[] args) throws IOException {
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

        //★这种初始化方法跟C#有的一拼：
        var lstInit04 = List.of(
                new Person(1, "A"),
                new Person(2, "B"),
                new Person(2, "B")
        );

        //演示：添加元素的方法，单个的，List的，实现IEnumerable<T>接口的
        lstInit01.add("A");

        lstInit02.add("B");
        lstInit02.add("C");

        lstInit02.addAll(List.of("4", "5", "6")); //不能addAll数组，C#可以
        lstInit02.addAll(lstInit01);

        //以下几个生成不可修改的List。当add,remove时会报错
        var lstInitNCopy = Collections.nCopies(10, "ABC"); //ABC COPY10份，这是测试用的功能吧

        var lst_ReadOnly_1 = Arrays.asList(new String[]{"1", "2", "3", "3"});

        var lst_ReadOnly_2 = Arrays.asList("1", "2", "3", "3");

        var lst_ReadOnly_3 = List.of("1", "2", "3", "3"); //★推荐用这个，原因：短小精悍。

        //初始化后还想改，那就再包装下吧。
        var lstInit05 = new ArrayList<>(List.of("1", "2", "3", "3")); //★推荐
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
                //级联排序 https://www.concretepage.com/java/jdk-8/java-8-stream-sorted-example
                .sorted(Comparator.comparing(Person::getName).thenComparing(Person::getId).reversed())
                //.sorted((x, y) -> x.getId() > y.getId() ? 1 : -1) //这种写法看着简单，但不严谨，几十个项时就会报错，因 Comparator<T>契约要求：compare(x,y)==-compare(y,x)
                //.sorted((x, y) -> x.getId() > y.getId() ? 1 : x.getId() < y.getId() ? -1 : 0) //要这么写
                .skip(1) //跳过
                .limit(10) //限制
                .map(p -> new Person(p.getId(), p.getName())) //没有类似C#的匿名类，所以还是转为原生的吧
                .collect(Collectors.toMap(p -> p.getId(), p -> p)); //转为map


        //Distinct的默认扩展不适合复杂类型，这个是自己写的方法
        var lst14 = lstInit03.stream().filter(ComparatorHelper.distinct(p -> p.getId())).collect(Collectors.toList());

        //演示：GroupBy的更强大的实现
        Map<String, List<Person>> lst15 = lstData.stream().limit(10).collect(Collectors.groupingBy(p -> {
            //这里的内容就是构造Key的，最终返回的Key相同的会放到同一组
            if (p.getHeight().compareTo(BigDecimal.valueOf(2)) > 0) {
                return "Height>2";
            } else {
                return "Height<=2";
            }
        }));

        //演示：相当于线程安全的ArrayList
        var lstUnModify = Collections.unmodifiableCollection(lstData); //变成只读集合还有上面一堆.of()的也是只读集合

        var lstSync = Collections.synchronizedList(List.of(1, 2, 3)); //读少写多，这个性能高，读写都锁住

        var conlist = new CopyOnWriteArrayList<Person>(); //读多写少，这个性能高，只锁住写操作
        if (conlist.isEmpty()) {
            conlist.add(new Person());
        }


        //java经常需要 List转Array,所以有此节
        String[] strs1 = List.of("1", "2", "3", "4").toArray(String[]::new);
        String[] strs2 = List.of("1", "2", "3", "4").toArray(new String[0]);
        String[] strs3 = List.of("1", "2", "3", "4").stream().filter(p -> Integer.parseInt(p) % 2 == 0).toArray(String[]::new);

        //集合为空返回false，any就是至少有一个满足，如果都没有一个元素，当然不满足了
        var bool1=List.of().stream().anyMatch(p->p.toString().length()>0);
        var bool2=List.of().stream().anyMatch(p->p.toString().length()<0);
        var bool3=List.of().stream().anyMatch(p->p.toString().length()==0);

        //集合为空返回true，
        var bool4=List.of().stream().allMatch(p->p.toString().length()>0);
        var bool5=List.of().stream().allMatch(p->p.toString().length()<0);
        var bool6=List.of().stream().allMatch(p->p.toString().length()==0);

        System.in.read();
    }
}
