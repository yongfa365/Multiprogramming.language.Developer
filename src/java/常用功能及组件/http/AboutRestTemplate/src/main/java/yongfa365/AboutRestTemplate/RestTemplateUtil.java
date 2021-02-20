package yongfa365.AboutRestTemplate;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RestTemplateUtil {

    private static RestTemplate restTemplate = HttpClient.getRestTemplate();

    private static HttpHeaders headers = new HttpHeaders();

    {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
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

            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            outStreamStr = responseEntity.getBody();
            printStatus(outStreamStr, responseEntity);
        } catch (Exception e) {
            System.out.println("没有返回,报错：\n");
            e.printStackTrace();
        }
        return outStreamStr;
    }

    public static String doPostSsh(String title, String body, String url) {
        var outStreamStr = "";
        try {
            url = checkAndFixUrl(null, url);

            HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);

            outStreamStr = responseEntity.getBody();
            printStatus(outStreamStr, responseEntity);
        } catch (Exception e) {
            System.out.println("没有返回,报错：\n");
            e.printStackTrace();
        }
        return outStreamStr;
    }

    private static void printStatus(String outStreamStr, ResponseEntity<String> responseEntity) {
        if (200 <= responseEntity.getStatusCodeValue() && responseEntity.getStatusCodeValue() < 300) {
            System.out.println("正常返回的Body:\n" + outStreamStr);
        } else {
            System.out.println("异常返回的Body:\n" + outStreamStr);
        }
    }
}
