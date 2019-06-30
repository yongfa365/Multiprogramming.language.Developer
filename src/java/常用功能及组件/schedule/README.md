## schedule有几种实现方式？
    1.[JDK 原生实现] (AboutSchedule/src/main/java/yongfa365/ScheduleInJDK.java)
        1.1. Timer（不推荐，语法略复杂）
    
        1.2. ScheduledExecutorService
            - scheduleAtFixedRate
            - scheduleWithFixedDelay
        
    2. [Spring @Scheduled（推荐，简单全面）](AboutSchedule/src/main/java/yongfa365/ScheduleInSpring.java)
        - cron
        - fixedRate
        - fixedDelay
        
    3. Quartz
        - mem
        - jdbc 分布式
    

都是单线程的，与C#行为不同(C#的timer每次都会开个线程)，java想实现C#的效果也很容易：方法内部再开个线程执行就行了。更多细节看具体代码吧。

## 记住这个就行啦
功能|简要说明
--|--
fixedDelay<br />scheduleWithFixedDelay|不管执行多久，完成后都要等固定间隔再执行。
fixedRate<br />scheduleAtFixedRate|到点就执行，如果上一个没执行完就等着，上个完成后他就立即执行，**会堆积**。<br />
cron|到点就执行，如果上一个没执行完就跳过本次。


## Spring @Scheduled

    - Scheduled里long的单位都是ms
    - fixedRate、fixedDelay、cron同时只能有一个
    - pom.xml没有要求
```java
@EnableScheduling //要启用下
@SpringBootApplication
public class ScheduleInSpring {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleInSpring.class, args);
    }
}    
```

```java
@Scheduled(cron = "*/2 * * * * *")

@Scheduled(fixedDelay = 3000, initialDelay = 3000)

@Scheduled(fixedRate = 3000)
public static void taskByFixedRate() {
    log.info("taskByFixedRate");
}
```
