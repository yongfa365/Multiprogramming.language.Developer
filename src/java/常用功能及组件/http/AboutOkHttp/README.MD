

## 官方网站
|id|desc|url|
|---|----|----|
|1|官网|https://square.github.io/okhttp/|
|2|官方例子|https://github.com/square/okhttp/tree/master/samples/guide/src/main/java/okhttp3/recipes|


## 使用方法
```java
//同步调用
 httpClient.newCall(request).execute(); 

 //异步调用
 httpClient.newCall(request).enqueue(new Callback() {})
 ```
 
 
## Q/A

Q：OkHttpClient是否可以设置为单例？

A：是的， [OkHttpClients Should Be Shared](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/#okhttpclients-should-be-shared) 

Q：超时设置是在OkHttpClient上的，而不是Request的，难道要设置多个单例？太麻烦了吧！

 A：确实，解决方案：[以源OkHttpClient为模板再改改就行](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/#customize-your-client-with-newbuilder)

