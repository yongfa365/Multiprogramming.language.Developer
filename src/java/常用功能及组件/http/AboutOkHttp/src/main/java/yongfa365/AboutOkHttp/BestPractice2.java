package yongfa365.AboutOkHttp;

import okhttp3.*;

import java.io.IOException;

public class BestPractice2 {
    public static void main(String[] args) {
        //这里写了一堆url，只是便于测试，最终只会用最后一个，自行注释掉其他的来测试
        Request request = new Request.Builder()
                .url("http://asdfasdfasdfasfe333333.com/") //可以测试dns问题
                .url("https://self-signed.badssl.com/") //可以用原生的okhttpclient测试假证书问题
                .url("http://httpstat.us/500?sleep=5000") //测试延时
                .url("http://httpstat.us/404")
                .url("http://httpstat.us/502")
                .url("http://httpstat.us/200")
                .header("Accept", "application/json")  //测试httpstat.us时需要加这个，不然获取到的body是空
                .build();

        OkHttpClient httpClient = HttpClient.trustAllSslOkHttpClient();
        //httpClient = new OkHttpClient(); //原生client，默认会校验证书

        Call call = httpClient.newCall(request);


        //enqueue是异步调用
        call.enqueue(new Callback() {
            //这两个里都会返回，要么直接报错，要么是500返回之类的
            @Override
            public void onFailure(Call call, IOException e) {
                //证书错、dns错、等其他错误
                System.out.println("没有返回,报错：\n");
                e.printStackTrace();
            }

            //只要是Server响应了，就会进入这里，包括：400,403,404,500,502,503等
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body() != null ? response.body().string() : null;
                if (response.isSuccessful()) { // 200<=statusCode<300
                    //200响应的进这里
                    System.out.println("正常返回的Body:\n" + body);
                } else {
                    //400,403,404,500,502,503等响应进这里
                    System.out.println("异常返回的Body:\n" + body);
                }
            }
        });
    }
}
