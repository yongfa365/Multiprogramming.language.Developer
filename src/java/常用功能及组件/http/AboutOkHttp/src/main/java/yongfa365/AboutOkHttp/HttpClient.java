package yongfa365.AboutOkHttp;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import yongfa365.AboutOkHttp.Interceptor.HttpLoggingInterceptor;
import yongfa365.AboutOkHttp.Interceptor.LoggingEventListener;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


public class HttpClient {

    //在web程序里，连接池得是静态的才能重用，官方测试用例：
    //https://github.com/square/okhttp/blob/master/okhttp-testing-support/src/main/java/okhttp3/TestUtil.java#L44
    private static final ConnectionPool CONNECTION_POOL = new ConnectionPool();


    public OkHttpClient OkHttpClient;
    public StringBuffer HttpLog = new StringBuffer();
    public StringBuffer EventLog = new StringBuffer();

    private HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
        HttpLog.append("\n").append(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss.SSS"))).append("           ").append(message);
    });

    private LoggingEventListener.Factory loggingEvent = new LoggingEventListener.Factory(message -> {
        //不使用EventLog而是都用HttpLog，将两个日志合并在一起
        HttpLog.append("\n").append(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss.SSS"))).append(" ").append(message);
    });

    public HttpClient() {
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .eventListenerFactory(loggingEvent)
                .sslSocketFactory(IgnoreTsl.SOCKET_FACTORY, IgnoreTsl.TRUST_ALL_MANAGER)
                .hostnameVerifier((hostname, session) -> true)
                .build();

    }

    public String body(Response response) {
        if (httpLoggingInterceptor.getLevel() == HttpLoggingInterceptor.Level.BODY) {
            return httpLoggingInterceptor.getBody();
        } else {
            try {
                return response.body() != null ? response.body().string() : null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static OkHttpClient trustAllSslOkHttpClient() {

        //通过newBuilder来重用连接池
        return DEFAULT_OK_HTTP_CLIENT.newBuilder()
                .retryOnConnectionFailure(true) //TODO: 默认就是true，需要研究下在什么场景会用到这个，会重试多少次
                .build();
    }


    public static final OkHttpClient DEFAULT_OK_HTTP_CLIENT = new OkHttpClient().newBuilder()
            //  .connectionPool(connectionPool) // 使用默认配置 https://github.com/square/okhttp/blob/master/okhttp-testing-support/src/main/java/okhttp3/TestUtil.java#L44
            .connectTimeout(Duration.ofSeconds(10))// default 10s
            .readTimeout(Duration.ofSeconds(10))// default 10s
            .writeTimeout(Duration.ofSeconds(10))// default 10s
            .sslSocketFactory(IgnoreTsl.SOCKET_FACTORY, IgnoreTsl.TRUST_ALL_MANAGER)
            .hostnameVerifier((hostname, session) -> true)
            .retryOnConnectionFailure(true) // default true
            .build();

}
