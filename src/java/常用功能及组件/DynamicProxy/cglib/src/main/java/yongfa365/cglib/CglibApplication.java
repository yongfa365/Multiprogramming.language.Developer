package yongfa365.cglib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.NamingPolicy;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;


public class CglibApplication {

    public static void main(String[] args0) throws IOException, InterruptedException {
        // CGLIB 动态代理调用
        CglibProxy cglibProxy = new CglibProxy();
        Panda panda = (Panda) cglibProxy.getInstance(new Panda());
        panda.eat();


        var cglibProxy1=(HashMap) Enhancer.create(HashMap.class, (MethodInterceptor) (obj, method, args, proxy) -> {
            System.out.println("调用前2");
            var result = proxy.invokeSuper(obj, args);
            System.out.println("调用后2");
            return result;
        });
        cglibProxy1.size();
        cglibProxy1.put("","");


    }

}


class Panda {
    public void eat() {
        System.out.println("The panda is eating");
    }
}

class CglibProxy implements MethodInterceptor {
    private Object target;

    public Object getInstance(Object target) {
        this.target = target;

        Enhancer enhancer = new Enhancer();
        // 设置父类为实例类
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("调用前");
        Object result = methodProxy.invokeSuper(o, objects); // 执行方法调用
        System.out.println("调用后");
        return result;
    }
}

