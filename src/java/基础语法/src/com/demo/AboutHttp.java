package com.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.Duration;
import java.util.zip.GZIPInputStream;


// 使用Fiddler监控Java的http,https请求:
//      http://note.youdao.com/noteshare?id=b4301bb2f5309498818f3edf5ca84514&sub=8A5FF02CF58D418E9717C35527247A3B
//
// ★★★★★如果header不需要Origin，Referer，那自带的功能还可以，如果你有采集的需求、爬虫的需求则得用http://hc.apache.org/了
public class AboutHttp {
    public static void RunDemo() throws Exception {
        GetDemo();
        PostDemo();
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
                //  .header("Origin", "https://www.baidu.com") //禁止设置Origin★★★★★不让爬别人网站？
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36")
                .header("Content-Type", "application/json")
                //   .header("Referer", "https://www.cnblogs.com/") //禁止设置Referer★★★★★不让爬别人网站？
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
                //  .header("Origin", "https://www.baidu.com") //禁止设置Origin★★★★★不让爬别人网站？
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36")
                .header("Content-Type", "application/json")
                //   .header("Referer", "https://www.cnblogs.com/") //禁止设置Referer★★★★★,不让爬别人网站？
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
