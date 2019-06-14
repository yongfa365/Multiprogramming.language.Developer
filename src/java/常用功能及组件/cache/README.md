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
  - [OriginalDemo1_Simple](http://www.google.com/)
  - [OriginalDemo2_Refresh](http://www.google.com/)
  
  ### spring-boot-starter-cache使用方法
  - [缓存方法结果，配置文件控制](http://www.google.com/)
  - [自定义多个bean](http://www.google.com/)
