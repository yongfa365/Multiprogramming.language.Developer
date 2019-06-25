## 官方网站
id|desc|url
--|----|----
1|lombok官网.features|https://projectlombok.org/features/all
2|lombok官网.experimental|https://projectlombok.org/features/experimental/all
3|lombok官网.github|https://github.com/rzwitserloot/lombok
4|lombok官网.github|https://github.com/rzwitserloot/lombok
5|lombok-intellij-plugin|https://github.com/mplushnikov/lombok-intellij-plugin

## 为什么使用它
 - 减少代码量，从而减少出错几率
 - builder方式使代码更简洁，清晰
 - 依靠稳定的组件提升系统的健壮性
 

## 配置
```xml
<!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.8</version>
    <scope>provided</scope>
</dependency>
```

## 原理
 - lombok是在编译时完成字节码的修改的，运行时并不需要。
 - lombok默认没有提供ide智能提示及消除错误指示的功能，要用第三方lombok-intellij-plugin解决