package com.demo.Others.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class LoggingDynamicProxy implements InvocationHandler {

    public static void main(String[] args) {
        Map<String,String> map = LoggingDynamicProxy.newInstance(Map.class, new HashMap<String, String>());
        map.put("111", "3333");
        map.size();
        map.clear();


        List<String> list = LoggingDynamicProxy.newInstance(List.class, new ArrayList<String>());
        list.add("234");
        list.addAll(List.of("", ""));
        list.clear();
        list.size();
        list.remove("");
    }




    // 〓〓〓〓〓〓〓〓〓〓 首先，他是个Handler 要实现invoke方法 〓〓〓〓〓〓〓〓〓〓
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        long start = System.nanoTime();
        var targetMethod = cachedMethods.get(getSignature(method));
        targetMethod.setAccessible(true);

        Object result = targetMethod.invoke(target, args);
        long elapsed = System.nanoTime() - start;

        System.out.printf("Executing %s.%s finished in %s ns\n", method.getDeclaringClass(), method.getName(), elapsed);

        return result;

    }

    // 〓〓〓〓〓〓〓〓〓〓 其次，他还能初始化一个动态代理，方便外界调用 〓〓〓〓〓〓〓〓〓〓
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> interfaceClass, Object target) {
        var handler = new LoggingDynamicProxy(target);
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, handler);
    }

    private final Map<String, Method> cachedMethods = new HashMap<>();

    private Object target;

    private LoggingDynamicProxy(Object target) {
        this.target = target;

        for (Method method : target.getClass().getDeclaredMethods()) {
            this.cachedMethods.put(getSignature(method), method);
        }
    }


    private String getSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getName()).append(" ");
        sb.append(method.getReturnType()).append(" ");
        for (Class<?> parameterType : method.getParameterTypes()) {
            sb.append(parameterType.getName()).append(" ");
        }
        return sb.toString();
    }


}