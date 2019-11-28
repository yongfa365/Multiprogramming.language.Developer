package yongfa365.AboutOkHttp;

import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class InterceptorDemo {
    public static void main(String[] args) {

        //这里写了一堆url，只是便于测试，最终只会用最后一个，自行注释掉其他的来测试
        Request request = new Request.Builder()
                .url("http://asdfasdfasdfasfe333333.com/") //可以测试dns问题
                .url("https://self-signed.badssl.com/") //可以用原生的okhttpclient测试假证书问题
                .url("http://httpstat.us/500?sleep=5000") //测试延时
                .url("http://httpstat.us/404")
                .url("http://httpstat.us/502")
                .url("http://httpstat.us/200")
                .url("https://cdn.sstatic.net/Sites/stackoverflow/img/favicon.ico?v=4f32ecc8f43d")
                .url("https://www.baidu.com")
                .header("Accept", "application/json")  //测试httpstat.us时需要加这个，不然获取到的body是空
                .header("Accept-Encoding", "gzip, deflate")
                .build();

        var httpClient = new HttpClient();

        try {
            //只要是Server响应了，就会有Response，包括：400,403,404,500,502,503等
            Response response = httpClient.OkHttpClient.newCall(request).execute(); //同步调用
            String body = httpClient.body(response);
            if (response.isSuccessful()) { // 200<=statusCode<300
                //200响应的进这里
                System.out.println("正常返回的Body:\n" + body);
            } else {
                //400,403,404,500,502,503等响应进这里
                System.out.println("异常返回的Body:\n" + body);
            }
        } catch (IOException e) {
            //证书错、dns错、等其他错误
            System.out.println("没有返回,报错：\n");
            e.printStackTrace();
        } finally {
            System.out.println(httpClient.HttpLog);
            System.out.println(httpClient.EventLog);
        }

    }
}
