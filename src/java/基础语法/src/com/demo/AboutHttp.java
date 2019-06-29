package com.demo;

import com.demo.Helper.Helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.zip.GZIPInputStream;


// 使用Fiddler监控Java的http,https请求:
//      http://note.youdao.com/noteshare?id=b4301bb2f5309498818f3edf5ca84514&sub=8A5FF02CF58D418E9717C35527247A3B
//
// 11.0.1及之前的几个版本header禁止Origin、Referer不好用可以用http://hc.apache.org/替代。
// 11.0.2解决了这个bug,按官方的说法他比apache的httpclient更好，与lambda结合也更好
public class AboutHttp {
    public static void main(String[] args) throws Exception {
        SimpleDemo();
        GetDemo();
        PostDemo();

        sendWithException();
    }

    public static void SimpleDemo() throws Exception {

        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder(URI.create("https://www.cnblogs.com")).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static void PostDemo() throws Exception {

        var url = "https://www.cnblogs.com";
        var filename = "D:\\1.html";
        var body = "{a:1,b:2}";


        var client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(5000))
                .build();

        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                //.header("Host", "www.cnblogs.com") //禁止设置
                //.header("Connection", "keep-alive") //禁止设置
                //.header("Content-Length", "113") //禁止设置
                .header("Accept", "application/json, text/plain, */*")
                .header("Origin", "https://www.baidu.com") //11.0.2之前的几个版本禁止设置？
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36")
                .header("Content-Type", "application/json")
                .header("Referer", "https://www.cnblogs.com/") //11.0.2之前的几个版本禁止设置？
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", " zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7")
                .header("Cookie", "a=1;b=3")

                //UploadString
                .POST(HttpRequest.BodyPublishers.ofString(body))

                ////UploadData
                //.POST(HttpRequest.BodyPublishers.ofByteArray(body.getBytes()))

                ////UploadFile
                //.POST(HttpRequest.BodyPublishers.ofFile(Path.of(filename)))

                .version(HttpClient.Version.HTTP_1_1)
                .timeout(Duration.ofMillis(5432))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var rsCode = response.statusCode();
        var rsBody = response.body();
        var rsHeaders = response.headers();

        var allcookie = String.join(",", response.headers().allValues("Set-Cookie"));

    }

    public static void GetDemo() throws Exception {
        var url = "https://www.cnblogs.com";
        var filename = "D:\\1.html";
        var body = "{a:1,b:2}";


        var client = HttpClient.newBuilder().build();

        var request = HttpRequest.newBuilder().uri(URI.create(url))
                //.header("Host", "www.cnblogs.com") //禁止设置
                //.header("Connection", "keep-alive") //禁止设置
                //.header("Content-Length", "113") //禁止设置
                .header("Accept", "application/json, text/plain, */*")
                .header("Origin", "https://www.baidu.com") //11.0.2之前的几个版本禁止设置？
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36")
                .header("Content-Type", "application/json")
                .header("Referer", "https://www.cnblogs.com/") //11.0.2之前的几个版本禁止设置？
                // .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", " zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7")
                .header("Cookie", "a=1;b=3")
                .build();

        if (request.headers().allValues("Accept-Encoding").toString().contains("gzip")) {            //使用gzip的场景
            var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            var rsBody = InputStreamToString(response.body());
            var rsCode = response.statusCode();
            var rsHeaders = response.headers();
            rsHeaders.map().forEach((key, value) -> System.out.println(key + ":" + String.join(",", value)));

            var allcookie = String.join(",", response.headers().allValues("Set-Cookie"));
        } else {
            //DownloadFile
            client.send(request, HttpResponse.BodyHandlers.ofFile(Path.of(filename)));

            //DownloadData
            client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            //DownloadString
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var rsBody = response.body();

            //Async
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println);
            ;
        }


    }

    public static void sendWithException() {

        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://asdfasdfasdfasfe333333.com/")) //可以测试dns问题
                .uri(URI.create("https://self-signed.badssl.com/")) //可以用原生的okhttpclient测试假证书问题
                .uri(URI.create("http://httpstat.us/500?sleep=10000")).timeout(Duration.ofSeconds(5))//测试延时
                .uri(URI.create("http://httpstat.us/404"))
                .uri(URI.create("http://httpstat.us/502"))
                .uri(URI.create("http://httpstat.us/200"))
                .header("Accept", "application/json")  //测试httpstat.us时需要加这个，不然获取到的body是空
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var body = response.body();

            //TODO:貌似所有的httpClient类库都是这样的：只要有响应就是正常的。与C#不同:
            //   2XX、300X（java与C#都认为是正常）；4XX、5XX(java认为正常，C#认为异常)；dns、timeout、ssl错（Java与C#都认为是异常）

            if (200 <= response.statusCode() && response.statusCode() < 300) {
                //200响应的进这里
                System.out.println("正常返回的Body:\n" + body);
            } else {
                //400,403,404,500,502,503等响应进这里
                System.out.println("异常返回的Body:\n" + body);
            }
        } catch (Exception e) {
            //证书错、dns错、等其他错误
            var err = Helper.getStackTrace(e);
            System.out.println("没有返回,报错：\n" + err);
        }
    }

    public static String InputStreamToString(InputStream in) throws IOException {
        try (var out = new ByteArrayOutputStream();
             var ungzip = new GZIPInputStream(in)) {
            var buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            return out.toString();
        }


    }
}
