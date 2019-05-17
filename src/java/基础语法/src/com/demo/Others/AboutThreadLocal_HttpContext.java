package com.demo.Others;

import java.util.Map;


public class AboutThreadLocal_HttpContext {
    public static void main(String[] args) {
        new Thread(() -> {
            //可以使用aop拿到http的上下文信息，然后放到自定义的HttpContextCurrent
            var heads = "我是heads1{referer:http://www.abc.com/,a:1,b:2}";
            var body = "我是body1";
            HttpContextCurrent.setRequestHeads(heads);
            HttpContextCurrent.setRequestBody(body);
            var request = HttpContextCurrent.getRequestAsString();
            System.out.println("\n\n\n" + request);
        }).start();

        new Thread(() -> {
            //可以使用aop拿到http的上下文信息，然后放到自定义的HttpContextCurrent
            var heads = "我是heads2{referer:http://www.abc.com/,a:1,b:2}";
            var body = "我是body2";
            HttpContextCurrent.setRequestHeads(heads);
            HttpContextCurrent.setRequestBody(body);
            var request = HttpContextCurrent.getRequestAsString();
            System.out.println("\n\n\n" + request);
        }).start();
    }
}


//Demo 3：实现类似HttpContext的功能，参考：http://www.jasongj.com/java/threadlocal/
class HttpContextCurrent {
    private static ThreadLocal<HttpContext> session = ThreadLocal.withInitial(() -> new HttpContext());

    public static String getRequestBody() {
        return session.get().Request.BodyAsString;
    }

    public static String getRequestHeads() {
        return session.get().Request.HeadsAsString;
    }

    public static void setRequestBody(String body) {
        session.get().Request.BodyAsString = body;
    }

    public static void setRequestHeads(String heads) {
        session.get().Request.HeadsAsString = heads;
    }


    static String getRequestAsString() {
        var item = session.get();
        return String.format(
                "【Request.Url】\r\n%s\r\n【Request.Heads】\r\n%s\r\n【Request.Body】\r\n%s}",
                item.Request.Url, item.Request.HeadsAsString, item.Request.BodyAsString
        );
    }

    static class HttpContext {
        HttpContext() {
            this.Request = new RequestOrResponse();
            this.Response = new RequestOrResponse();
        }

        RequestOrResponse Request;
        RequestOrResponse Response;
    }

    static class RequestOrResponse {
        String Url;
        String BodyAsString;
        String HeadsAsString;
        Map<String, String> Heads;
    }
}