package yongfa365.ExtensionMethods;

import lombok.experimental.ExtensionMethod;
import yongfa365.ExtensionMethods.ExtensionMethods;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


//lombok-intellij-plugin 还不支持@ExtensionMethod因为idea没提供相关切入点，所以在idea里会报错，但不影响使用。
// https://github.com/mplushnikov/lombok-intellij-plugin/issues/21
@ExtensionMethod(ExtensionMethods.class)
public class Tester {

    public  static  DateTimeFormatter Formatter =DateTimeFormatter.ofPattern("yyyy年MM月dd日");

    public static void main(String[] args) {
        //原生写法
        System.out.println(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")));
        System.out.println(ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE));
        System.out.println(ZonedDateTime.now().format(Formatter));

        //扩展方法写法
        System.out.println(ZonedDateTime.now().toString("yyyy年MM月dd日"));
        System.out.println(LocalDateTime.now().toString("yyyy年MM月dd日"));
        System.out.println(LocalDate.now().toString("yyyy年MM月dd日"));
        System.out.println(LocalTime.now().toString("HH:mm:ss"));
        String str1 = "\t\n  　sdfsdf\t\n  　";
        System.out.println(str1.trimOrNull());
    }
}
