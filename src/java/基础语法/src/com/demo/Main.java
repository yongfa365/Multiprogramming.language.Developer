package com.demo;

import java.util.Scanner;

public class Main {
    //C#只能有一个Main方法，而Java可以有多个
    //https://docs.oracle.com/javase/specs/index.html
    public static void main(String[] args) throws Exception {
        //类相关的，直接看Entity目录下的内容吧
        //一下一个一个的F10看吧
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
        new Scanner(System.in).nextLine();
    }


}
