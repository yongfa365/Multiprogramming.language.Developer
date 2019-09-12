package yongfa365.AboutApacheHttpClient.common;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

public class ApacheHttpClientUtil {

    private static CloseableHttpClient httpclient;

    static{
        List<BasicHeader> headers = new ArrayList<>();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
        httpclient = HttpClients.custom().setDefaultHeaders(headers).build();
    }

    private static String checkAndFixUrl(String orderNo, String url) {
        if (Strings.isBlank(url)) {
            throw new IllegalArgumentException("调用接口时 url 为空");
        }

        if (url.contains("?")) {
            url += "&";
        } else {
            url += "?";
        }

        if (!Strings.isBlank(orderNo)) {
            url += "__tracking__OrderNo_PackageOption=" + orderNo;
        } else {
            url += "__tracking__From_PackageOption";
        }

        return url;
    }

    public static String doGetSsh(String title, String url) {
        var outStreamStr = "";
        try {
            url = checkAndFixUrl(null, url);

            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpGet);
            outStreamStr = EntityUtils.toString(closeableHttpResponse.getEntity());
            printStatus(outStreamStr, closeableHttpResponse);
        } catch (Exception e) {
            System.out.println("没有返回,报错：\n");
            e.printStackTrace();
        }
        return outStreamStr;
    }

    private static void printStatus(String outStreamStr, CloseableHttpResponse closeableHttpResponse) {
        if (200 <= closeableHttpResponse.getStatusLine().getStatusCode() && closeableHttpResponse.getStatusLine().getStatusCode() < 300) {
            System.out.println("正常返回的Body:\n" + outStreamStr);
        } else {
            System.out.println("异常返回的Body:\n" + outStreamStr);
        }
    }

    public static String doPostSsh(String title, String body, String url) {
        var outStreamStr = "";
        try {
            url = checkAndFixUrl(null, url);

            HttpPost httpPost = new HttpPost(url);
            HttpEntity httpEntity = new StringEntity(body, ContentType.APPLICATION_JSON);
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost);

            outStreamStr = EntityUtils.toString(closeableHttpResponse.getEntity());
            printStatus(outStreamStr, closeableHttpResponse);
        } catch (Exception e) {
            System.out.println("没有返回,报错：\n");
            e.printStackTrace();
        }
        return outStreamStr;
    }
}
