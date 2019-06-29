package com.demo;


import java.util.List;
import java.util.stream.IntStream;

public class AboutThread_Parallel {
    public static void main(String[] args) {
        //并行，很简单.parallel()就行
        IntStream.range(1, 10).parallel().forEach(System.out::println);

        List.of(1, 2, 3, 4, 5).stream().parallel().forEach(System.out::println);
    }
}
