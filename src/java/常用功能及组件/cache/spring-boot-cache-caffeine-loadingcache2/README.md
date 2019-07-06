## 为什么有这个项目
spring-boot-cache默认不支持LoadingCache，但我们希望的是数据还没过期时调用当前方法刷一份新的就行，但缓存默认没有用aop所以实现不了？


## 实现方法
使用aop实现，具体代码：[**自定义CacheManager**](src/main/java/yongfa365/config/CaffeineConfig.java)

没有使用默认配置，直接用就行了

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-aop</artifactId>
</dependency>

```
```java
//spring-boot默认加了spring.aop.auto: true，所以这句不是必须的
@EnableAspectJAutoProxy
```

```java
@CacheablePlus(name = "可以不要", expireAfterWrite = 20, refreshAfterWrite = 5, maximumSize = 1000, recordStats = true)
String getDataWithCaffeineLoadingCache(String input) {...}
```
