package com.demo.Others;

import com.demo.Entity.Person;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class AboutWeakReference {
    public static void main(String[] args) throws Exception {
        runWeakReferenceDemo1();
        runWeakReferenceDemo2();
    }

    public static void runWeakReferenceDemo1() throws InterruptedException {
        // 创建一个对象，并与之建立 强引用
        var person = new Person(1, "yongfa365");

        // 与原始对象建立 弱引用
        var ref = new WeakReference<>(person);

        System.gc();
        Thread.sleep(1000);
        System.out.println("虽然gc了，但因为对象有个强引用，所以并不会被删除：" + ref.get());

        // 断开强引用，之后对象只有一个弱引用了
        person = null;

        System.gc();
        Thread.sleep(1000);

        System.out.println("对象只有弱引用时，便会被gc释放："+ref.get());
    }

    //public到处可用，private是只有这个类内部可用，不写则表示package内可用（这个与C#不同）
    public static void runWeakReferenceDemo2() throws InterruptedException {
        // 强引用
        var person = new Person(1, "yongfa365");

        // 弱引用hashmap
        var map = new WeakHashMap<Person, Integer>(); //弱引用，在key为null后这条记录就会被回收
        map.put(person, 111);
        System.out.println(map.size()); //1

        // 强引用实例设置为null后，WeakHashMap将在gc后自动移除弱引用项，所以size()变为0了
        // 如果将上面的WeakHashMap替换为HashMap，此时即便person=null了，但其实例依然被HashMap强引用，所以gc也不会回收
        person = null;
        System.gc();
        Thread.sleep(1000);

        System.out.println(map.size());

    }

}
