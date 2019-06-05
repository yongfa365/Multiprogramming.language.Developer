配置文件一般是这样规划的：
```
application.yml
application-dev.yml
application-test.yml
application-uat.yml
application-prod.yml
```

但这些配置里不应该存放敏感信息，如各种连接字符串：MySQL、Sql Server、MongoDB、Redis、RabbitMQ、ElasticSearch等。

解决方法是：将敏感信息单独存放在另一个profile如：application-prod-**secret**.yml，这个配置文件不会提交到git（dev除外，要不怎么调试？），一般人也看不到里面的内容，只在部署时用工具如jenkins放入目标位置，这样就可以保证不泄密啦！？。

然后在启动时的命令行参数指定：spring.profiles.active=**prod,prod-secret**，或者在application-prod.yml里指定：
```
spring.profiles.include:
  - prod-secret
```

最终配置文件就拆成了这样：

```
application.yml

application-dev.yml
application-dev-secret.yml

application-test.yml
application-test-secret.yml （源码没有，部署时加入）

application-uat.yml
application-uat-secret.yml （源码没有，部署时加入）

application-prod.yml
application-prod-secret.yml （源码没有，部署时加入）
```



