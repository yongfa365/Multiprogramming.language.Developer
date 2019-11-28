package yongfa365.AboutOkHttp;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class IgnoreTsl {
    public final static SSLContext SSL_CONTEXT = createSslContext();
    public final static SSLSocketFactory SOCKET_FACTORY = SSL_CONTEXT.getSocketFactory();
    public final static SSLParameters SSL_PARAMETERS= createSslParameters();
    public final static TrustAllManager TRUST_ALL_MANAGER = new TrustAllManager();



    public static void disableHostnameVerification() {
        // disableHostnameVerification : https://stackoverflow.com/questions/52988677/allow-insecure-https-connection-for-java-jdk-11-httpclient
        System.getProperties().setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());
    }

    private static SSLParameters createSslParameters() {
        var sslPara = new SSLParameters();
        sslPara.setEndpointIdentificationAlgorithm("");
        return sslPara;
    }

    private static SSLContext createSslContext() {
        var trustAllManager = new TrustAllManager();
        try {
            var sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{trustAllManager}, new SecureRandom());
            return sc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


}
