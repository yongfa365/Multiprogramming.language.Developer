package com.demo;

import com.demo.Entity.Person;

import java.util.*;
import java.util.stream.Collectors;

//HashSet TreeSet
public class AboutCollection_Set {
    public static void main(String[] args) {
        //C#的HashSet<T>可以指定比较器，Java.HashSet不能，得用TreeSet<T>
        var hsInit1 = new HashSet<String>();
        var hsInit2 = new HashSet<String>(List.of("1", "2", "3", "3"));

        //★这种初始化方法跟C#有的一拼：
        var hsInit3 = Set.of("1", "2", "3"); //如果有重复项，运行时会报错

        //TreeSet<T>使用方法与HashSet<T>类似，但★他是有序的，★必须有比较器否则运行时报错，如：类继承自Comparable<T> 或 TreeSet<T>初始化时指定个Comparator
        //TreeSet<T>性能比HashSet<T>差一点
        var tsInit2 = new TreeSet<String>(List.of("C", "A", "B", "C")); //[A, B, C] 排序了
        var tsInit3 = new TreeSet<String>(String::compareToIgnoreCase);
        tsInit3.addAll(List.of("A", "a", "b")); //["A","b"]

        //自定义比较器
        var tsInit4 = new TreeSet<Person>(Comparator.comparing(Person::getId));
        var tsInit5 = new TreeSet<Person>((x, y) -> x.getId().equals(y.getId()) && x.getName().equals(y.getName()) ? 0 : 1);
        var tsInit6 = new TreeSet<Person>(ComparatorHelper.bool((x, y) -> x.getId().equals(y.getId()) && x.getName().equalsIgnoreCase(y.getName())));

        var tsTest = tsInit6;  //可以切换成tsInit5,tsInit6试试看最终的结果
        tsTest.add(new Person(1, "A"));
        tsTest.add(new Person(1, "A"));
        tsTest.add(new Person(1, "B"));
        tsTest.add(new Person(2, "B"));


        var h3 = new HashSet<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};

        var b1 = h3.contains(3);
        var h4 = h3.stream().filter(p -> p > 1).collect(Collectors.toSet());

        //集合“并行”执行
        h3.stream().parallel().forEach(System.out::println);

        System.out.println("断点用");

    }
}
