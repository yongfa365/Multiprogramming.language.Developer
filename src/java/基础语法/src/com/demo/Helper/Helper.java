package com.demo.Helper;



import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


public class Helper {
    public static void NoErrorInvoke(Consumer act)
    {
        try
        {
            act.accept("没办法Action呀，就是要写个参数");

        }
        catch (Exception ex)
        {
            System.out.println(ex); //写日志
        }
    }

}
