package com.demo;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

//io与nio的不同要写下，因为同时存在。我现在看到的是：nio已经可以很方便的满足我的要求了，没必要研究旧io了
public class AboutFile {
    public static void main(String[] args) throws Exception {
        System.out.println("控制台输入输出，随便输入个，回车结束:");
        var input = new Scanner(System.in).nextLine();
        System.out.println("你输入了：" + input);


        var filepath = "C:\\FileTest\\haha\\1.txt";
        var context = "内容";
        var lstContext = List.of("A", "B");
        var byteContext = context.getBytes(StandardCharsets.UTF_8);

        //获取当前工作目录
        var workingDirectory = System.getProperty("user.dir");

        //虽然能能改变这个配置，但Path.of等在启动时已经将其固化了，再改也不会用他。而C#可以很容的修改
        System.setProperty("user.dir", "C:\\windows");

        //仅当前目录
        var files1 = Files.list(Path.of("C:\\Windows\\")).filter(p -> p.toString().toLowerCase().endsWith(".exe"));

        //包括子目录，可以指定层次
        var files2 = Files.walk(Path.of("C:\\Windows\\"), 10);

        //包括子目录，可以指定层次，可以指定filter。path.endsWith与path.toString().endsWith不同
        var files3 = Files.find(Path.of("C:\\Windows\\"), 10,
                (filePath, fileAttr) -> filePath.toString().endsWith(".exe") && fileAttr.isRegularFile());

        // createDirectories 如果目录已经存在不会报错
        Files.createDirectories(Path.of("C:\\FileTest\\haha"));


        if (!Files.exists(Path.of(filepath))) {
            //xx
        }

        var path = Path.of(filepath);
        System.out.println(path.getParent()); // C:\FileTest\haha
        System.out.println(path.toFile().getParent()); // C:\FileTest\haha

        Files.write(Path.of(filepath), context.getBytes(StandardCharsets.UTF_8),
                //不存在则创建，存在则附加。
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        Files.write(Path.of(filepath), context.getBytes(StandardCharsets.UTF_8));
        Files.write(Path.of(filepath), lstContext);

        //快速写大量内容
        try (var writer = Files.newBufferedWriter(Path.of("C:\\bigtest.txt"),
                StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            var temp = "12345".repeat(1024);
            for (int i = 0; i < 100000; i++) {
                writer.append(temp);
                writer.newLine();
            }
        }
        Files.delete(Path.of("C:\\bigtest.txt"));

        //读取utf-8 with bom文件后，字符串开头自动加了个不可见的字符。
        //如有需要请自行去掉原文件的bom或者读取后替换下：tmp.replace("\uFEFF", "")，JDK不解决此问题
        var cnt1 = Files.readString(Path.of(filepath));
        var cnt2 = Files.readAllLines(Path.of(filepath));
        var cnt3 = Files.readAllBytes(Path.of(filepath));

        Files.copy(Path.of(filepath), Path.of(filepath + ".txt"), StandardCopyOption.REPLACE_EXISTING);
        Files.delete(Path.of(filepath));


        var tempPath = Path.of("C:", "a", "b", "c.txt"); //C:\a\b\c.txt
        var filename = tempPath.getFileName();
        var fullpath = tempPath.toAbsolutePath();

        //获取扩展名没有像C#里的Path.GetExtension(tempPath);
        // Guava：Files.getFileExtension(filepath);
        // Apache Commons IO：FilenameUtils.getExtension(filepath);
        int dotIndex = filepath.lastIndexOf('.');
        var extend = (dotIndex == -1) ? "" : filepath.substring(dotIndex + 1);

    }
}
