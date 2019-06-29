package com.demo;


import com.demo.Entity.Person;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

//HashMap 和 TreeMap
public class AboutCollection_Map {
    public static void main(String[] args) {
        //C#的Dictionary<K,V>可以指定比较器，Java.HashMap不能，得用TreeMap
        var hashMap1 = new HashMap<Integer, String>();
        hashMap1.put(1, "111");
        hashMap1.put(2, "222");
        hashMap1.put(3, "333");


        var hashMap2 = new HashMap<Integer, String>() {{
            put(1, "111");
            put(2, "222");
            put(3, "333");
        }};

        //★这种初始化方法跟C#有的一拼：可以接受10对
        var hashMap3 = Map.of(
                1, "111",
                2, "222",
                3, "333"
        );

        //可以接受N对
        var hashMap4 = Map.ofEntries(
                Map.entry(1, "111"),
                Map.entry(2, "222"),
                Map.entry(3, "333")
        );


        //TreeMap使用方法与HashMap类似，但★他是有序的，必须有比较器否则运行时报错，如：类继承自Comparable<T> 或 TreeMap初始化时指定个Comparator
        //TreeMap性能比HashMap差一点
        var treeMap2 = new TreeMap<String, String>(String::compareToIgnoreCase) {{
            put("B", "333");
            put("A", "111");
            put("a", "222");
        }}; //{A=222, B=333} 奇怪吧，key相同的再次添加时将Value更新了，而不是整个更新，也不是不更新。

        //自定义比较器
        var tmInit4 = new TreeMap<Person, Integer>(Comparator.comparing(Person::getId));
        var tmInit5 = new TreeMap<Person, Integer>((x, y) -> x.getId().equals(y.getId()) && x.getName().equals(y.getName()) ? 0 : 1);
        var tmInit6 = new TreeMap<Person, Integer>(ComparatorHelper.bool((x, y) -> x.getId().equals(y.getId()) && x.getName().equalsIgnoreCase(y.getName())));

        var tsTest = tmInit4;  //可以切换成tsInit5,tsInit6试试看最终的结果
        tsTest.put(new Person(1, "A"), 1);
        tsTest.put(new Person(1, "A"), 1);
        tsTest.put(new Person(1, "B"), 1);
        tsTest.put(new Person(2, "B"), 1);


        var dict = new HashMap<String, Integer>();
        dict.put("1", 2);//添加赋值
        dict.replace("1", 3);       // dict["a"] = 1; //Java不能这么写，C#可以
        dict.put("2", 2);
        dict.put("2", 3);//key重复不会报错，直接替换了，与C#不同


        if (dict.containsKey("a")) {
            System.out.println(dict.get("a"));
        }

        var keys = dict.keySet();
        var values = dict.values();

        //线程安全的
        var condict = new ConcurrentHashMap<Integer, Person>();
        condict.put(1, new Person(1, "111"));
        condict.putIfAbsent(1, new Person(1, "222"));
        condict.put(1, new Person(1, "333")); //key重复不会报错，直接替换了，与C#不同
        condict.remove(1);
        condict.put(1, new Person(2, "444"));


        //集合“并行”执行
        hashMap1.entrySet().stream().parallel().forEach(System.out::println);
        condict.entrySet().stream().parallel().forEach(System.out::println); //这个println比C#好用，会调用obj.toString();
        System.out.println("断点用");

    }
}
