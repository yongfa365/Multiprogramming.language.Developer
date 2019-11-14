package com.demo.Others;

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
        var proxy = new AnimalProxy();

        var dogProxy = (Animal) proxy.getInstance(new Dog());
        dogProxy.eat();
    }
}

interface Animal {
    void eat();
}

class Dog implements Animal {
    @Override
    public void eat() {
        System.out.println("The dog is eating");
    }
}

class Cat implements Animal {
    @Override
    public void eat() {
        System.out.println("The cat is eating");
    }
}

/**
 * JDK 代理,需要实现接口InvocationHandler及用Proxy.newProxyInstance创建代理对象
 */
class AnimalProxy implements InvocationHandler {
    private Object target;

    public Object getInstance(Object target) {
        this.target = target;
        var clazz = target.getClass();

        // 取得代理对象
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
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

        System.out.println("调用前");
        Object result = method.invoke(target, args);
        System.out.println("调用后");

        return result;
    }
}
