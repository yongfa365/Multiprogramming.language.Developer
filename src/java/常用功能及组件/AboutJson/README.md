## 官方网站
id|desc|url
---|----|----
1|jackson|https://github.com/FasterXML/jackson
2|gson谷歌的|https://www.baeldung.com/jackson-vs-gson
3|fastJson|https://github.com/alibaba/fastjson


```java
// jackson 有统一处理日期的格式，如果想自定义可以在字段的getXXX上单独指定
public class OrderLog {
    private LocalDateTime operateTime;

    // 只是对
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public LocalDateTime getOperateTime() {
        return operateTime;
    }
}
```

