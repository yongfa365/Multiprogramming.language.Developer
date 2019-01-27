package com.demo; //没有命名空间，按包来分的。

public class AboutClass {
    //每个类里都可以有个main入口，可以直接从这里启动，
    // 整个包的入口是哪个可以在META-INF/MANIFEST.MF里指定，如Main-Class: com.demo.Main
    public static void main(String[] args) throws Exception {
        // -javaagent可以动态改字节码，看着很好。
        // https://stackoverflow.com/questions/33631419/replace-a-class-within-the-java-class-library-with-a-custom-version
        // https://blog.csdn.net/wild46cat/article/details/78917647
    }
}
