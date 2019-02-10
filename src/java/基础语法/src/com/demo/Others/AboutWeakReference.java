package com.demo.Others;

import com.demo.Entity.Person;

import java.util.WeakHashMap;

public class AboutWeakReference {
    public static void main(String[] args) throws Exception {
        RunDemo();
    }

    public static void RunDemo() throws InterruptedException {
        RunWeakReferenceDemo();
    }

    //public到处可用，private是只有这个类内部可用，不写则表示package内可用（这个与C#不同）
    public static void RunWeakReferenceDemo() throws InterruptedException {
        //强引用
        var person = new Person(1, "yongfa365");

        //弱引用hashmap
        var whmap = new WeakHashMap<Person, Integer>();
        whmap.put(person, 111);
        System.out.println(whmap.size());

        //强引用实例设置为null后，WeakHashMap将在gc后自动移除弱引用项，所以size()变为0了
        //如果将上面的WeakHashMap替换为HashMap，此时即便person=null了，但其实例依然被HashMap强引用，所以gc也不会回收
        person = null;
        System.gc(); //不会立即回收，所以要sleep一会再看
        Thread.sleep(1000);
        System.out.println(whmap.size());

    }

}
