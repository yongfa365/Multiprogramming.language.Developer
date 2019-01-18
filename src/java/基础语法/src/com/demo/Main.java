package com.demo;

import java.util.Scanner;

public class Main {

    //https://docs.oracle.com/javase/specs/index.html
    public static void main(String[] args) throws Exception {
        //可以像C#一样，如下，一个一个进入看
        // ★也可以进入类内部运行各自的main.
        // C#与Java都允许有多个main方法，Java的IDEA在每个main左边都放了个运行的图标，点击后就可以运行，很方法。但vs没这么方便。

        AboutBasic.RunDemo();
        AboutDateTime.RunDemo();
        AboutString.RunDemo();
        AboutCollection.RunDemo();

        AboutFile.RunDemo();
        AboutHttp.RunDemo();
        AboutThread.RunDemo();
        AboutClass.RunDemo();


        //region Jet Brains IDEA has this feature
        { //这个花括号只是为了隔离用，可以忽略

        }
        //endregion

        //以下内容不要注释，否则异步时看不到效果
        System.out.println("Console测试结束，Press Enter Exit");
        System.in.read();
    }


}
