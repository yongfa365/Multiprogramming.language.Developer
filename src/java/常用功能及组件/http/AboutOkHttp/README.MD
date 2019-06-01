httpClient.newCall(request).execute(); //同步调用
httpClient.newCall(request).enqueue(new Callback() {}) //异步调用

要将用例写完，与原生httpclient一样，这样才算基本合格，然后还需要写一些他自身特性的代码，
官网地址等

测试http请求，证书几个好网站：
https://httpbin.org/status/503
https://badssl.com/
http://httpstat.us/502