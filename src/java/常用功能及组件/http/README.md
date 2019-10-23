## 主流HttpClient框架比较
- 主要研究过JDK及OkHttp，以下对HttpComponents的评价可能不够客观。
- google反对HttpComponents因设计复杂，而认可OkHttp且默认继承到android。
- 大家都认可google，所以选OkHttp不会错。
- 虽然现在[排行第二](https://mvnrepository.com/open-source/http-clients)，但他比第一出来晚多了，足见大势所趋。

|Feature|[JDK HttpClient](https://openjdk.java.net/groups/net/httpclient/intro.html)|[Apache HttpClient](http://hc.apache.org/)|[OkHttp](https://square.github.io/okhttp/)|
|----|----|----|----|
|Supporter|Oracle|社区(springboot集成了它))|Android默认集成(被google认可)|
|诞生时间|2018年|2007年前|2013年|
|活跃度|<a title="3年一个稳定版">☆</a>|<a title="1年2-4次">☆☆☆</a>|<a title="1月1-2次">☆☆☆☆☆☆</a>|
|文档|☆☆|☆☆☆|☆☆☆☆☆|
|Compress(gzip)|☆☆|☆☆☆☆|☆☆☆☆☆|
|MultiPart|✘|✔|✔|
|UrlBuilder|✘|✔|✔|
|Intercept|✘|✔|✔✔✔|
|Event|✘|✘|✔✔✔|
|日志详细度|--|--|<a title="dns解析，连接，发送，接收时间全都有，很为企业级用户考虑">☆☆☆☆☆</a>|
|依赖外部|0|2个共1300K|4个共800K|
|maven引用数|--|89K|48K|
|GitHub Star|--|1K主要没在这维护|35K|

## 测试http请求、证书几个好网站：
https://httpbin.org/status/503
https://badssl.com/
http://httpstat.us/502
