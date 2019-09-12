package yongfa365.AboutApacheHttpClient;

public class BestPractice {
    public static void main(String[] args) {

        ApacheHttpClientUtil.doGetSsh("调用外部接口 GET 测试200",  "http://httpstat.us/200");
        ApacheHttpClientUtil.doGetSsh("调用外部接口 GET 测试404",  "http://httpstat.us/404");
        ApacheHttpClientUtil.doGetSsh("调用外部接口 GET 测试500",  "http://httpstat.us/500");
        ApacheHttpClientUtil.doGetSsh("调用外部接口 GET 测试没配置url",  "   ");
        ApacheHttpClientUtil.doGetSsh("调用外部接口 GET 测试跳过https",  "https://package.test.wingontravel.com/robots.txt");
        ApacheHttpClientUtil.doGetSsh("调用外部接口 GET 测试dns解析",  "http://asdfasdfasdfasfe333333.com/");
        ApacheHttpClientUtil.doGetSsh("调用外部接口 GET 测试200.超时",  "http://httpstat.us/200?sleep=100000");


        ApacheHttpClientUtil.doPostSsh("调用外部接口 POST 测试200", "我是body",  "http://httpstat.us/200");
        ApacheHttpClientUtil.doPostSsh("调用外部接口 POST 测试404", "我是body",  "http://httpstat.us/404");
        ApacheHttpClientUtil.doPostSsh("调用外部接口 POST 测试500", "我是body",  "http://httpstat.us/500");
        ApacheHttpClientUtil.doPostSsh("调用外部接口 POST 测试没配置url", "我是body",  "   ");
        ApacheHttpClientUtil.doPostSsh("调用外部接口 POST 测试跳过https", "我是body",  "https://package.test.wingontravel.com/robots.txt");
        ApacheHttpClientUtil.doPostSsh("调用外部接口 POST 测试dns解析", "我是body",  "http://asdfasdfasdfasfe333333.com/");
        ApacheHttpClientUtil.doPostSsh("调用外部接口 POST 测试200.超时", "我是body",  "http://httpstat.us/200?sleep=100000");




    }
}
