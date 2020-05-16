package com.demo.Others;

import java.util.List;
import java.util.Optional;

//https://blog.kaaass.net/archives/764
//https://www.oracle.com/technetwork/articles/java/java8-optional-2175753.html
//https://howtodoinjava.com/java8/java-8-optionals-complete-reference/
class AboutOptional {
    public static void main(String[] args) {
        new AboutOptional().RunDemo();
    }

    public void RunDemo() {
        var computer = new Computer();
        var version = "";

        //理想中是这么写（没判断null会有问题）：
        //version = computer.getSoundcard().getUSB().getVersion();

        //据说kotlin是这么写的（很优雅的解决了空引用的问题）：
        //version = computer?.getSoundcard()?.getUSB()?.getVersion();

        //C#是这么写的（调用属性，而不是方法，更简洁）：
        //version = computer?.Soundcard?.USB?.Version;

        //现实是，你在用古董java！所以：

        //java 1.8以前是这么写的（回到原始社会了）：
        if (computer != null) {
            Soundcard soundcard = computer.getSoundcard();
            if (soundcard != null) {
                USB usb = soundcard.getUSB();
                if (usb != null) {
                    version = usb.getVersion();
                }
            }
        }

        //java 1.8是这么写的（进化到石器时代了，java11里也还是这么用的）：
        version = Optional.ofNullable(computer)
                .map(p -> p.getSoundcard())
                .map(p -> p.getUSB())
                .map(p -> p.getVersion())
                .orElse(null);

        //存在才打印,C#虽然没提供，但直接写个扩展方法就把这个秒杀了
        Optional.ofNullable(version).ifPresent(System.out::println);

        var version2 = Optional.ofNullable(version).orElse("APP7.2");

        //java 1.9有了if else写法:
        Optional.ofNullable(version).ifPresentOrElse(
                p -> System.out.println(p), //存在则执行
                () -> System.out.println("null") //不存在则执行
        );


        var lst = List.of(1, 2, 3);

        Integer integer1 = lst.stream().filter(p -> p > 3).findFirst().get(); //ide会警告，如果不想报错则需要ifPresent或isPresent下再取
        Integer integer2 = lst.stream().filter(p -> p > 3).findFirst().orElseThrow();//不存在时就报错，既然你这么清楚那IDE就没理由警告了。
        Integer integer3 = lst.stream().filter(p -> p > 3).findFirst().orElse(123);//  给个默认值
        Integer integer4 = lst.stream().filter(p -> p > 3).findFirst().orElseGet(() -> {return 1 + 1;});//给个supplier

        lst = null;
        Optional.ofNullable(lst).ifPresent(System.out::println);
    }


    class Computer {
        private Soundcard soundcard;

        public Soundcard getSoundcard() {
            return soundcard;
        }
    }

    class Soundcard {
        private USB usb;

        public USB getUSB() {
            return usb;
        }
    }

    class USB {
        private String version;

        public String getVersion() {
            return version;
        }
    }
}
