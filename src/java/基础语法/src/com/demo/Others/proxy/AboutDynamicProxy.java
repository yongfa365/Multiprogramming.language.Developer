package com.demo.Others.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//动态代理=动态+代理。
// 动态：是指运行时，相对于编译时。
// 代理：是指代替、替代
//       如常见的机票代理，没代理时：你到机场买机票 --> 有代理后：你到携程买机票，携程代替你到机场买机票。
// 携程的好处：比价，简化流程，减少耗时，不用问机场直接回复你有没有票。。。
public class AboutDynamicProxy {
    public static void main(String[] args) {

        System.out.println("================静态代理Demo=====================");
        var staticProxy = new AnimalStaticProxy(new Dog());
        staticProxy.eat();
        staticProxy.speak();

        System.out.println("================动态代理Demo=====================");
        var dynamicProxy = (Animal) new AnimalDynamicProxy().newInstance(new Dog());
        dynamicProxy.eat();
        dynamicProxy.speak();

        System.out.println("================动态代理Demo2=====================");
        var target = new Dog();
        var dynamicProxy2 = (Animal) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), //Animal
                (proxy1, method1, args1) -> {
                    System.out.println("动态代理2.调用前");
                    Object result = method1.invoke(target, args1);
                    System.out.println("动态代理2.调用后");
                    return result;
                });
        dynamicProxy2.eat();
        dynamicProxy2.speak();

    }
}

interface Animal {
    void eat();

    void speak();
}

class Dog implements Animal {
    @Override
    public void eat() {
        System.out.println("The dog is eating");
    }

    @Override
    public void speak() {
        System.out.println("The dog is speaking");
    }
}

class Cat implements Animal {
    @Override
    public void eat() {
        System.out.println("The cat is eating");
    }

    @Override
    public void speak() {
        System.out.println("The cat is speaking");
    }
}


////////////////////////////////////////////////静态代理//////////////////////////////////////////////////////

/**
 * 静态代理：针对要代理的接口写个代理类，也就就是普通的Impl实现类，构造函数接收目标对象，方法的实现也直接调用目标对象方法。
 */
class AnimalStaticProxy implements Animal {
    Animal target;

    public AnimalStaticProxy(Animal target) {
        this.target = target;
    }

    @Override
    public void eat() {
        System.out.println("静态代理.调用前");
        this.target.eat();
        System.out.println("静态代理.调用后");
    }

    @Override
    public void speak() {
        System.out.println("静态代理.调用前");
        this.target.speak();
        System.out.println("静态代理.调用后");
    }
}


////////////////////////////////////////////////动态代理//////////////////////////////////////////////////////

/**
 * JDK 代理,需要实现接口InvocationHandler及用Proxy.newProxyInstance创建代理对象
 */
class AnimalDynamicProxy implements InvocationHandler {
    private Object target;

    public Object newInstance(Object target) {
        this.target = target;
        // 取得代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    /**
     * ★★★★★因为所有方法调用都会经过这里，所以可以当AOP用，场景：
     * 1.调用目标方法，2.记录日志（error|perf|payload），3.缓存，4.权限验证，5.动态写个实现（mybatis）
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            // 诸如hashCode()、toString()、equals()等方法，将target指向当前对象this
            return method.invoke(this, args);
        }

        System.out.println("动态代理.调用前");
        //调用目标方法，当然也可以不调用目标方法，自己造个
        Object result = method.invoke(target, args);
        System.out.println("动态代理.调用后");

        return result;
    }
}
