package yongfa365.AboutOkHttp;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;


public class HttpClient {

    //在web程序里，连接池得是静态的才能重用，官方测试用例：
    //https://github.com/square/okhttp/blob/master/okhttp-testing-support/src/main/java/okhttp3/TestUtil.java#L44
    private static final ConnectionPool CONNECTION_POOL = new ConnectionPool();

    private static final TrustManager[] TRUST_ALL_CERTS = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };

    private static final SSLContext TRUST_ALL_SSL_CONTEXT;

    static {
        try {
            TRUST_ALL_SSL_CONTEXT = SSLContext.getInstance("TLS"); //TLS比SSL版本高，应该是兼容SSL的
            TRUST_ALL_SSL_CONTEXT.init(null, TRUST_ALL_CERTS, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    //阻止new的方式初始化
    private HttpClient() {
    }

    private static final SSLSocketFactory TRUST_ALL_SSL_SOCKET_FACTORY = TRUST_ALL_SSL_CONTEXT.getSocketFactory();

    public static OkHttpClient trustAllSslOkHttpClient() {

        return new OkHttpClient().newBuilder()
                .connectionPool(CONNECTION_POOL) //TODO:要了解下最佳实践，这个池有什么用
                .connectTimeout(10000, TimeUnit.MILLISECONDS) //默认10s
                .readTimeout(10000, TimeUnit.MILLISECONDS) //默认10s
                .sslSocketFactory(TRUST_ALL_SSL_SOCKET_FACTORY, (X509TrustManager) TRUST_ALL_CERTS[0])
                .hostnameVerifier((hostname, session) -> true)
                .retryOnConnectionFailure(true) //TODO: 默认就是true，需要研究下在什么场景会用到这个，会重试多少次
                .build();

    }
}
