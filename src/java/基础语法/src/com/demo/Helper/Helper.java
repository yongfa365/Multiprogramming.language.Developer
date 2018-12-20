package com.demo.Helper;


import java.util.function.Consumer;
import java.util.stream.BaseStream;


public class Helper {
    public static void NoErrorInvoke(Consumer act) {
        try {
            act.accept("没办法Action呀，就是要写个参数");
        } catch (Exception ex) {
            System.out.println(ex); //写日志
        }
    }

    public static <T, S extends BaseStream<T, S>> void Dump(BaseStream<T, S> input) throws Exception {
        var iterator = input.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        //反射演练
        //var methods = input.getClass().getMethods(); //getClass 得到的是真实类型，而我们要的是接口的类型，所以此方案不行
        //for (var method:methods) {
        //    if (method.getName().equals("forEach")) {
        //        Consumer<Object> aaa = p -> System.out.println(p);
        //        method.invoke(input, aaa);
        //        return;
        //    }
        //}
    }


}
