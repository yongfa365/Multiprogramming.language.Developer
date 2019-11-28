package com.demo.Others;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AboutReflection {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class myClass;

        // 方式一：forName()—— JVM 查找并加载指定的类，也就是说JVM会执行该类的静态代码段
       // myClass = Class.forName("com.demo.Others.PeopleImpl");

        // 方式二：getClass()
        myClass = new PeopleImpl().getClass();

        // 方式三：.class直接获取
        myClass = PeopleImpl.class;

        // 调用静态（static）方法
        Method getSex = myClass.getMethod("getSex");
        getSex.invoke(myClass);

        // 调用普通方法
        Object obj = myClass.newInstance();
        Method sayHi = myClass.getDeclaredMethod("sayHi", String.class);
        sayHi.invoke(obj, "老王");


        // 调用私有方法
        Object object = myClass.newInstance();
        Method privateSayHi = myClass.getDeclaredMethod("privateSayHi");
        privateSayHi.setAccessible(true); // 修改访问限制
        privateSayHi.invoke(object);

        // 获取所有方法,无论public,private,static
        System.out.println("============getDeclaredMethods================");
        for (Method method : myClass.getDeclaredMethods()) {
            System.out.println(method);
        }

        // 获取所有字段
        System.out.println("============getDeclaredFields================");
        for (Field field : myClass.getDeclaredFields()) {
            System.out.println(field);
        }


        // 获取所有方法,无论public,private,static
        System.out.println("============getMethods================");
        for (Method method : myClass.getMethods()) {
            System.out.println(method);
        }

        // 获取所有字段
        System.out.println("============getFields================");
        for (Field field : myClass.getFields()) {
            System.out.println(field);
        }


        // Declared 获取当前类的变量或方法，private和public都可以获取到，但不能获取到父类任何信息
        // 非 Declared 的只能获取到 public 的变量或方法，并且可以获取到父类的

        //============getDeclaredMethods================
        //public static void com.demo.Others.PeopleImpl.getSex()
        //public void com.demo.Others.PeopleImpl.sayHi(java.lang.String)
        //private void com.demo.Others.PeopleImpl.privateSayHi()
        //============getDeclaredFields================
        //private java.lang.String com.demo.Others.PeopleImpl.privateSex
        //public java.lang.String com.demo.Others.PeopleImpl.publicZu
        //============getMethods================
        //public static void com.demo.Others.PeopleImpl.getSex()
        //public void com.demo.Others.PeopleImpl.sayHi(java.lang.String)
        //public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
        //public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
        //public final void java.lang.Object.wait() throws java.lang.InterruptedException
        //public boolean java.lang.Object.equals(java.lang.Object)
        //public java.lang.String java.lang.Object.toString()
        //public native int java.lang.Object.hashCode()
        //public final native java.lang.Class java.lang.Object.getClass()
        //public final native void java.lang.Object.notify()
        //public final native void java.lang.Object.notifyAll()
        //============getFields================
        //public java.lang.String com.demo.Others.PeopleImpl.publicZu
        //public static final int com.demo.Others.People.parentAge

    }
}

interface People {
    int parentAge = 18;

    void sayHi(String name);
}

class PeopleImpl implements People {
    private String privateSex = "男";
    public String publicZu = "汉族";

    @Override
    public void sayHi(String name) {
        System.out.println("hi," + name);
    }

    private void privateSayHi() {
        System.out.println("private SayHi");
    }

    public static void getSex() {
        System.out.println("18岁");
    }

}