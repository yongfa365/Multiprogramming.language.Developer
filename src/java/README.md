## 官方网站
id|desc|url
--|----|----
1|Oracle官方文档入口|https://docs.oracle.com/
2|JavaSE文档入口 会跳转到当前版本|https://docs.oracle.com/javase/
3|Language and VM  有html及pdf可下载|https://docs.oracle.com/javase/specs/
4|教程|https://docs.oracle.com/javase/tutorial/
5|API Documentation 各包的介绍|https://docs.oracle.com/en/java/javase/11/docs/api/index.html
6|jshell|https://docs.oracle.com/javase/10/jshell/ <br> https://docs.oracle.com/javase/10/tools/jshell.htm <br>或者随便找个类psvm吧。.net官方的没有类似的，有个不错的第三方收费的LinqPad
7|springboot的各种配置|https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#common-application-properties
8|纯洁的微笑：Spring Boot 使用的各种示例，以最简单、最实用为标准|https://github.com/ityouknow/spring-boot-examples

## 所谓的：生态
**C#**：默认组件已经做的相当出色了，自己搞定了所有功能且做到了优秀，让别的厂商无事可做，假如算100分。他已经做的这么好了，就算你有个更好的想法可能只是不停的+1，但只是+1力度不大，你的组件就不容易出名，最终看起来好像就是周边发展的不行。一言以蔽之：**月明星稀**。

**Java**：语法繁杂更新缓慢算50分，但开源的理念好，用的人多，不好的就重新封装一个，反正都能看到源码，改就是了，最终一些大公司就出品了一堆优秀的组件，当你想造一个轮子的时候发现已经有了，忍不住想干一票大的造个语言替代java时，发现也有了Kotlin、Groovy、Scala。然后最终表现就是：50+30+10+15+........很容易超过了C#。一言以蔽之：**众星璀璨**、**百花齐放**。

Java的方式也有缺陷：自身不足第三方补足，然后Java后续版本又加上了第三方的内容可能类名都相同，就冲突了。Java后来发展好了第三方的组件可能就鸡肋了，或者一堆公司实现了同样的功能从而乱花渐欲迷人眼了。

## 学习方法
所以学Java与学C#不同，想要用好C#只要学下官方默认组件就基本满分了，简单高效。而要学好Java则得：1.学好默认组件，有些是不用学的，具体哪些你要能分辨，2.熟悉第三方组件如：File，Http,Json,String,Collection，Lombok，框架如Spring。

Java在IDEA里F12可以直接看到源代码，很容易了解内部实现，有不清楚的很快就能搞定。而C#不能看源码影响深入研究，所以他官方文档写的很好，当然也可以用ILSpy反编译看，但这就不方便了。从这个角度看Java更贴合我的人生信条，不懂就搞懂，有路径可寻。
## 常用的第三方库有
Name|URL|Desc
----|----|----
Apache Commons Lang|https://commons.apache.org/|Commons的开发者会尽其所能地减少组件与其它开发库的依赖，让部署这些组件更加容易。除此之外，Commons组件还会尽可能保持接口的稳定，让Apache的用户（包括使用Commons的其它Apache项目）可以使用时无需担心未来可能的变化。内容包括：二进制，各种编码，字符串操作，集合扩展与增强，压缩解压，文件操作等。
Apache HttpComponents|https://hc.apache.org/|HttpClient,**以前这个很方便，但java 11里有了自己的httpclient后，他的这个优势被削弱了**
Google Guava|https://github.com/google/guava/wiki http://ifeve.com/google-guava/|Guava工程包含了若干被Google的 Java项目广泛依赖 的核心库，例如：集合 [collections] 、缓存 [caching] 、原生类型支持 [primitives support] 、并发库 [concurrency libraries] 、通用注解 [common annotations] 、字符串处理 [string processing] 、I/O 等等。 所有这些工具每天都在被Google的工程师应用在产品服务中。 **guava以前有优势，但被java8收编了一些核心功能（抄袭）后，guava已经没那么必要了TODO：要研究下**
Jackson|https://github.com/FasterXML/jackson-databind/|json序列化与反序列化的
Caffeine|https://github.com/ben-manes/caffeine|据说比guava的cache好，TODO

## 写Demo消耗时间
以下是具体某一项功能研究时间，实际时间比这个要多一些，因为要学习相关周边功能。

内容|耗时|说明
----|----|----
Basic|1天|主要是BigDecimal的与C#不同，花了些时间。
DateTime|2天|场景多，不灵活，要不停调试。
String|3天|场景多，加解密算法2天：调试C#与Java，使其互通，用C#加密再用Java解密，及反向操作。
Collection|5天|List花了1天，因为集合的通用操作都是用List演示的。Stream，Lambda，FunctionalInterface，AnonymousClass等一堆周边东西，因与C#不同都要学习花了3天，自带的FunctionalInterface理解断断续续也花了1天时间, [我汇总的函数式接口](http://note.youdao.com/noteshare?id=75ff9d93ea4d3dd3ec0352a03968a214&sub=ACCAAACB664D4D338D96D086A1B10AEB)
File|1天|简单过了下，能实现自己要的功能。文件不存在则创建存在则Append花了些时间
Thread|1天|简单功能模仿C#一会就搞定了，但不同的比较多，需要细看下。
IDE学习|3天|常用配置,Font，Color，Theme，忽略大小写,滚动改变字体大小，Live Templates，Document URL，GIT刷新显示外部改动，仿VisualStudio改各种快捷键


java中的装箱拆箱相当普遍，int->Integer，而C#int->Int32是同一个，不存在装箱拆箱，C#一般只在int->string->int时才涉及到。

刚从C#转java觉得Java太封闭了，像lambda，C#那么好的命名你直接抄过来不就行了吗？为什么还要自己再造一套名字？你们不会去看下其他语言的命名，然后可以用就用，统一一下会死吗？随着学习的深入发现java不止不抄C#的，连基于他而开发的很有名的类库创造的好的命名也不抄。

C#多用静态方法，如：XXXHelper，扩展方法等。而java里则多用spring的IOC，因为像配置之类的都是在spring容器里完成的。

原则上是用到哪个就把哪个搞明白，确定下来，同一类一个确定后，以后换别的就参照着实现一遍，以便做到全覆盖。
完成的标识是：在github上写了demo及在微信公众号及今日头条写了文章。


## TODO List

- [x] Java 11
    - [x] Basic(8个原始类型|BigDecimal|uuid|Random|if|else|do|while|for|swith|enum|接口默认实现|匿名类|内部类)
    - [x] DateTime (LocalDateTime|Period|Duration|ChronoUnit|Timer@@format|parse)
    - [x] String(regex|StringBuilder@@trim,strip,split,join,xxx[1],repeat,PadLeft,PadRight,replace,StartsWith,EndsWith,Contains,Substring,IsNullOrWhiteSpace,IsNullOrEmpty,性能)
    - [x] File(Files|Path|encoding)虽然比以前有改进，但还是太难用了，就不是给人用的
    - [x] Http(HttpClient|Sync|Async|gzip|headers|cookies|pool|SSL|proxy|get<T>|status400...body|DownloadString|UploadString)
    - [x] Collection(List|Set|Map|Queue|Stream|FunctionalInterface|lambda)
    - [x] Thread(野线程|ThreadPoolExecutor|parallel|Lock)
    - [x] Security(RSA|MD5|SHA|Base64|AES|DES)（耗时2天）

- [x] 周边工具
    - [x] jps
    - [x] jstack
    - [x] jshell
    - [ ] jconsole

- [x] Intellij IDEA
    - [x] 常用(滚动改变字体大小|悬停提示|去掉大小写敏感)
    - [x] Code Style
    - [x] live template(sout,cw)
    - [x] postfix（person.new.var）
    - [x] Keymap(同时融合Visual Studio的快捷键，两边使用同一套|全文搜索)
    - [x] git(Checkout|Merge|Branch|pull|push)
    - [x] plugin(lombok|Alibaba Java Coding Guidelines|Grep Console)
    - [x] Maven（mirror.aliyun|repository.dir|自行搭建仓库|各种命令|dependencyManagement|build.pluginManagement）（耗时3天）
    - [ ] Gradle
    
- [ ] JSON(嵌套引用|enum|null|LocalDateTime|pretty|性能|hashmap|所有基本类型|与C#的互通|多了个属性反序列化不报错|注解及规则相同否|反序列化成复杂类型List<List<HashMap>>)
    - [ ] jackson(ObjectMapper需要各种设置才好有，内置在spring里，鬼剑直接用这个)
    - [ ] [gson](https://www.baeldung.com/jackson-vs-gson)（google产的用起来还算方便，不过用spring多，暂时就不研究了）
    - [ ] fastJson（所谓的阿里出品的，虽然问题不断，但调用简单）

- [ ] Compress
    - [ ] snappy
    - [ ] gzip
    - [ ] zip
    - [ ] zlib
    - [ ] lz4


- [x] lombok（耗时1天）
    - [x] @Data、@Builder、@NoArgsConstructor、@Slf4j、@SuperBuilder、@ExtensionMethod


- [x] MyBatis（自动生成与手动分开|备注|事物|映射|插件开发）（耗时3天）
    - [x] 用resultType替代resultMap，能不用resultMap就不用
    - [x] sqlmap里尽可能扩大CDATA的范围
    - [x] 插件开发，intercept拦截器，实现分页功能、错误日志加上parameter、数据加解密等
        - Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
        - ParameterHandler (getParameterObject, setParameters)
        - ResultSetHandler (handleResultSets, handleOutputParameters)
        - StatementHandler (prepare, parameterize, batch, update, query)
    - [x] free mybatis generator插件， 在mapper接口与sqlmap间导航，不存在则告警，部分自动生成
    - [x] 集成tk.mybatis 使用通用Mapper，并写个SqlProvider支持批量插入
    - [x] 集成PageHelper
    
- [x] tk.MyBatis（耗时3天）
    - MBG能生成的方法他也能，且有增加，不生成XML，只要继承接口就能用。
    - **使用的是JPA注解，方便集成其他JPA实现，如：Spring Data Jpa 或QueryDsl等。**
    - 写MBG插件生成XML比较麻烦，而会写SqlMap那个XML的就会写扩展tk。
    - 默认方法名与MBG生成的相同，不影响自定义的接口及XML，0成本增强单表操作。
    - 语法里虽然可以强类型字段名，但值的类型是弱类型的，如：1写成"1212safda"也不能识别。
    - lambda版本的WeekEnd每一步都会执行反射解析ColumnName，没有缓存，性能堪忧。
    
- [x] MyBatisPlus（耗时1天）
    - 自己造的注解：@TableName,@TableId，@TableField,@Keyxxxx,也不计划向JPA靠齐，完全的自造轮子。
    - 增强的功能，名字也比较个性：insertAllColumn，updateAllColumn，不大喜欢。
    - 语法里虽然可以强类型字段名，但值的类型是弱类型的，如：1写成"1212safda"也不能识别。
    - 内部实现跟tk很像。
    - **lambda版本实现类似EF的表达式树，封装的更全如：groupby,having等，也直接是链式编程，还可以将表达式直接输出为最终的语句，方便测试。**
    - 只支持单主键，SqlServer是支持多主键的，所以跟主键相关的方法有些场景会有问题。
    
- [x] MyBatis genarator(MBG)使用，及其[插件开发](https://github.com/yongfa365/mybatis-generator-plugins) （耗时7天）
    - [x] 插件自动融合：生成的与手写的model,dao,sqlmap，上面放自动的，下面放手动。
    - [x] 支持SqlServer备注，tinyint映射为Integer
    - [x] 添加Jpa注解、IdClass
    - [x] 生成tk.mybatis风格文件

    
- [x] Cache
    - [x] Caffeine(耗时6天)
    - [x] Redis(Jedis封装的比较简单，命名也跟原生对应，更适合再包装)

- [ ] Spring  
    - [ ] Core（AOP|IOC ）
        - [ ] AOP(spring aop|cglib|aspectj|javaagent)
        - [ ] IOC
        - [ ] 动态代理
    - [=] SpringBoot(autoconfig,starter,parent)
        - [ ] 拆包（xxx.jar,config,lib）（1天）
    - [ ] Spring MVC|RestApi
    - [ ] Spring Data
      - [ ] 多数据源配置（MySQL|RabbitMQ|Redis）
      - [ ] JPA
      - [ ] querydsl
      - [ ] Mongodb（utc时间）
      - [ ] RabbitMQ（重连|LocalQueue|并发多个连接）
      - [ ] Kafka
      - [ ] ElasticSearch
    - [ ] actuator、SpringBootAdmin

- [x] http (Sync|Async|gzip|headers|cookies|pool|SSL|proxy|get<T>|status400...body)
    - [x] [jdk HttpClient](https://golb.hplar.ch/2019/01/java-11-http-client.html) 功能缺失(URI builder, multipart form data, form data, compression support)
    - [x] okHttp（Interceptor|有详细的每步耗时如：dns解析，建立安全链接，最终连接的IP等）
    - [ ] Spring RestTemplate 将被淘汰，不研究
    - [ ] Spring WebClient 有okhttp了就先不研究了
    - [ ] Apache httpClient 有okhttp了就先不研究了

- [x] Schedule（分布式|服务器时间差10秒任务只要3秒的重复执行问题）
    - [x] [Spring @Scheduled](https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling-annotation-support-scheduled)
    - [ ] [Quartz](https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling-quartz)被xxl-job取代了
    - [x] [xxl-job](https://github.com/xuxueli/xxl-job)与Quartz差不多,但增加了界面，可以看日志，有各种调度策略，能自动发现，有简单的权限管理

- [x] Excel
    - [x] EasyExcel(表头|列宽|身份证号|数字|日期时间格式|内存占用) 4h

- [ ] [data validator](https://hibernate.org/validator/)
- [ ] PDF
- [ ] 性能
    - [ ] APM
    - [ ] 火焰图
- [ ] html parse
- [x] doc
    - [x] swagger-ui
    - [x] javadoc 不需要刻意学，需要时找一个现有的抄下就行
