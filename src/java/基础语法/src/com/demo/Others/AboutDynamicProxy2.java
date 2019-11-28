package com.demo.Others;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AboutDynamicProxy2 {
    public static void main(String[] args) {
        var proxy = new AnimalProxy2();
        var animal = proxy.newInstance(Animal.class);
        animal.eat();
    }
}


class AnimalProxy2 implements InvocationHandler {

    @SuppressWarnings("unchecked")
    public <T> T newInstance(Class<T> clz) {
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Animal is eating  自己造的");
        return null;
    }
}
