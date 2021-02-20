package yongfa365.AboutRestTemplate;

public class BestPractice {
    public static void main(String[] args) {

        RestTemplateUtil.doGetSsh("调用外部接口 GET 测试200",  "http://httpstat.us/200");
        RestTemplateUtil.doGetSsh("调用外部接口 GET 测试404",  "http://httpstat.us/404");
        RestTemplateUtil.doGetSsh("调用外部接口 GET 测试500",  "http://httpstat.us/500");
        RestTemplateUtil.doGetSsh("调用外部接口 GET 测试没配置url",  "   ");
        RestTemplateUtil.doGetSsh("调用外部接口 GET 测试跳过https",  "https://package.test.wingontravel.com/robots.txt");
        RestTemplateUtil.doGetSsh("调用外部接口 GET 测试dns解析",  "http://asdfasdfasdfasfe333333.com/");
        RestTemplateUtil.doGetSsh("调用外部接口 GET 测试200.超时",  "http://httpstat.us/200?sleep=100000");


        RestTemplateUtil.doPostSsh("调用外部接口 POST 测试200", "我是body",  "http://httpstat.us/200");
        RestTemplateUtil.doPostSsh("调用外部接口 POST 测试404", "我是body",  "http://httpstat.us/404");
        RestTemplateUtil.doPostSsh("调用外部接口 POST 测试500", "我是body",  "http://httpstat.us/500");
        RestTemplateUtil.doPostSsh("调用外部接口 POST 测试没配置url", "我是body",  "   ");
        RestTemplateUtil.doPostSsh("调用外部接口 POST 测试跳过https", "我是body",  "https://package.test.wingontravel.com/robots.txt");
        RestTemplateUtil.doPostSsh("调用外部接口 POST 测试dns解析", "我是body",  "http://asdfasdfasdfasfe333333.com/");
        RestTemplateUtil.doPostSsh("调用外部接口 POST 测试200.超时", "我是body",  "http://httpstat.us/200?sleep=100000");




    }
}
