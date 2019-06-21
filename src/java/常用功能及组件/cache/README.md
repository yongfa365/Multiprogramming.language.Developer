# Getting Started
以下内容为自己的最佳实践，是有适用场景的，不可不信，不可全信。挑选适合自己的就行，不用非要有各种理论支撑。
## 理论
### 本地缓存的优势及缺点
  - 比分布式缓存快：不用网络传输，不用反序列化，GC少。
  - 就是本地对象，原生的，直接拿来就能用。
  - 调用各接口的各日志将急剧减少。
  
  - 可能占内存大：城市之前3000个突然有一天增加到60000个。
  - 需要设置更新策略，有些可能需要即时更新：黑名单更新。
  - 可能需要让特定类型缓存立即过期：产品价格更新。
  
### 什么数据应该放本地缓存？
  - 调用慢或耗资源 且 最终结果占内存不大的 且 访问频繁的，都可以将结果放本地缓存。
  - 系统配置：offline维护的各种系统或业务配置，配置中心拿来的、id与Name对应的字典表。
  - 反射结果：实体字段或属性的@Attribute最终形成的{Name:Desc}
  - 访问频繁：静态数据（国家信息，城市信息，供应商信息），动态数据(首页，产品列表页，产品详情页)
  
### 本地缓存关注点
  - 缓存初始化或穿透时，不准并发穿透，同一缓存只能一个穿透其他等待。
  - 缓存后应定期刷新而非过期刷新，从而保证仅首次慢，以后都快，数据也新鲜。
  - 要有手动清除机制

## 实战
### 为什么选中了Caffeine
  - 我在C#花了7天写了个CacheHelper，在java里发现Caffeine跟我的很像。
  - 看官方benchmark显示其：速度最快、支持刷新
  - 前身是google的guava的cache
  

id|desc|url
--|----|----
1|Caffeine|https://github.com/ben-manes/caffeine <br /> https://github.com/ben-manes/caffeine/wiki/Population
2|spring-boot-starter-cache|https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html

### Caffeine原生使用方法
  - [Demo1_Simple](AboutCaffeine/src/main/java/yongfa365/AboutCaffeine/Demo1_Simple.java)
  - [Demo2_Refresh](AboutCaffeine/src/main/java/yongfa365/AboutCaffeine/Demo2_Refresh.java)
  
### spring-boot-starter-cache使用方法
  - [默认使用方法](spring-boot-cache-default/src/main/java/yongfa365/springbootcachedefault/CacheHelper.java)
  - [spring-boot-starter-cache结合Caffeine的使用方法](spring-boot-cache-caffeine)
  - [自定义多个Bean使用Caffeine](TODO：)

### 核心代码展示
```xml
<!-- https://mvnrepository.com/artifact/com.github.ben-manes.caffeine/caffeine -->
<dependency>
	<groupId>com.github.ben-manes.caffeine</groupId>
	<artifactId>caffeine</artifactId>
	<version>2.7.0</version>
</dependency>
```
application.properties配置：
```yaml

#Cache白名单，不指定不限制。若指定则程序要么不用缓存，要么只能用这里的名称
spring.cache.cache-names=LocalCache_InMethod,cache12313

#此设置为10s后过期，然后得等填充完拿最新数据，填充速度慢就要等的时间长。
spring.cache.caffeine.spec=expireAfterWrite=5s,initialCapacity=10,maximumSize=1000

#不能用refresh
#spring.cache.caffeine.spec=expireAfterWrite=20s,refreshAfterWrite=10s
```
启动类加个注解：
```java
@EnableCaching //启动类要加这个注解
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```
缓存类直接用个@Cacheable：
```java
@Component
public class CacheHelper {
    //Cacheable的方式缓存过期后会因穿透而变慢，需要设置sync防止并发请求同时穿透。
    //默认CacheKey只考虑方法参数，极容易串，简单解决方案是显示的为每个都指定不同的key，但又不适用于有参数的方法。
    @Cacheable(value = "LocalCache_InMethod", sync = true, key="CacheHelper.getData")
    public Integer getData() {
        return LocalDateTime.now().getSecond();
    }
}
```
key的实现方案可以看下官方的[SimpleKeyGenerator.java](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/main/java/org/springframework/cache/interceptor/SimpleKeyGenerator.java)和[SimpleKey.java](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/main/java/org/springframework/cache/interceptor/SimpleKey.java) 这里用不好会有问题

