package yongfa365.core;

import lombok.extern.slf4j.Slf4j;
import yongfa365.core.annotation.Select;
import yongfa365.dao.DBMock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

// 动态代理,实现方法：结合这Proxy与InvocationHandler
@Slf4j
public class MapperProxy implements InvocationHandler {
    // 〓〓〓〓〓〓〓〓〓〓 首先，他是个Handler 要实现invoke方法 〓〓〓〓〓〓〓〓〓〓
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // hashCode()、toString()、equals()等方法，调用原生方法
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        // 其他方法拦截下来，由“代理”决定怎么响应。是查数据库还是缓存 “代理说了算”。

        // ============== Step 1: 从“注解”或“xml”或“解析domain”拿到对应的SQL ==============
        log.info("方法的签名:" + method.toString()); // 从xml拿: 可以根据签名去匹配
        var sql = method.getDeclaredAnnotation(Select.class).value(); // 从注解拿: 反射就行了
        log.info("注解上写的Sql:" + sql);


        // ============== Step 2: 从Method上拿到参数的注解、参数名、参数值，进行替换，生成可执行的SQL ==============
        var params = method.getParameters();
        for (int i = 0; i < params.length; i++) {
            var param = method.getParameters()[i];
            log.info("参数类型:{} 参数名称:{}  参数值:{}", param.getType(), param.getName(), args[i]);
            sql = sql.replace("#{" + param.getName() + "}", args[i].toString());
        }


        // ============== Step 3: 查数据库 ==============
        log.info("调用JDBC执行最终的Sql:" + sql);
        var dbresult = DBMock.executeSql(sql);


        // ============== Step 4: 将DB结果转为实体 ==============
        var returnType = method.getReturnType();
        log.info("将DB返回内容转为实体:" + returnType);

        // 实体要有个无参的构造函数(有参处理麻烦所以直接定死要无参的)。
        var result = returnType.getDeclaredConstructor().newInstance();

        var fields = returnType.getDeclaredFields();
        dbresult.forEach((column, value) -> Arrays.stream(fields)
                .filter(p -> p.getName().equalsIgnoreCase(column))
                .findFirst()
                .ifPresent(p -> {
                    try {
                        p.setAccessible(true);
                        p.set(result, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));

        return result;
    }



    // 〓〓〓〓〓〓〓〓〓〓 其次，他还能初始化一个动态代理，方便外界调用 〓〓〓〓〓〓〓〓〓〓
    @SuppressWarnings("unchecked")
    public static <T> T of(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, handler);
    }

    private static final MapperProxy handler = new MapperProxy();

    private MapperProxy() {
    }

}