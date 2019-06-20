## 为什么有这个项目
spring-boot-cache-starter默认只能在application.yml配置一种过期策略，
如：所有缓存都只能缓存5分钟。如果你想同时有缓存15分钟、60分钟的配置那就得自己写CacheManager实现了。

本项目写了个实现，方便快速扩充：
[**自定义CacheManager**](src/main/javayongfa365/config/CaffeineConfig.java)