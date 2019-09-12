package yongfa365.AboutWebClient.common;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class WebClientUtil {

    private static WebClient webClient = WebClient.builder()
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json; charset=utf-8")
            .build();

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

            ClientResponse clientResponse = webClient
                    .get()
                    .uri(url).exchange().block();

            outStreamStr = clientResponse.bodyToMono(String.class).block();
            printStatus(outStreamStr, clientResponse);
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

            ClientResponse clientResponse = webClient.post()
                    .uri(url)
                    .syncBody(body)
                    .exchange()
                    .block();
            outStreamStr = clientResponse.bodyToMono(String.class).block();
            printStatus(outStreamStr, clientResponse);
        } catch (Exception e) {
            System.out.println("没有返回,报错：\n");
            e.printStackTrace();
        }
        return outStreamStr;
    }

    private static void printStatus(String outStreamStr, ClientResponse clientResponse) {
        if (200 <= clientResponse.statusCode().value() && clientResponse.statusCode().value() < 300) {
            System.out.println("正常返回的Body:\n" + outStreamStr);
        } else {
            System.out.println("异常返回的Body:\n" + outStreamStr);
        }
    }
}
