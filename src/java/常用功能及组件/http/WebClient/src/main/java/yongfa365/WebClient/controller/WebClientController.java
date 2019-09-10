package yongfa365.WebClient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WebClientController {

    private static final String URL = "/test";

    @GetMapping("restTemplateTest")
    public Object getTest(Object object,HttpServletRequest request){
        Mono<String> stringMono = WebClient.builder()
                .baseUrl(getRequestSchemeHost(request) + URL + "?object=" + object)
                .build()
                .get()
                .retrieve()
                .bodyToMono(String.class);
        return stringMono;
    }

    @PostMapping("restTemplateTest")
    public Object postTest(Object object,HttpServletRequest request){
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("object",object);
        Mono<String> stringMono = WebClient.builder()
                .baseUrl(getRequestSchemeHost(request) + URL)
                .build()
                .post()
                .syncBody(paramMap)
                .retrieve()
                .bodyToMono(String.class);
        return stringMono;
    }

    @DeleteMapping("restTemplateTest")
    public Object deleteTest(Object object,HttpServletRequest request){
        String block = WebClient.builder()
                .baseUrl(getRequestSchemeHost(request) + URL + "?object=" + object)
                .build()
                .delete()
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return "delete:"+block;
    }

    @PutMapping("restTemplateTest")
    public Object putTest(Object object,HttpServletRequest request){
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("object",object);
        Mono<String> stringMono = WebClient.builder()
                .baseUrl(getRequestSchemeHost(request) + URL)
                .build()
                .put()
                .syncBody(paramMap)
                .retrieve()
                .bodyToMono(String.class);
        return stringMono;
    }

    /**
     * 获取uri前部分
     * @param request
     * @return
     */
    public static String getRequestSchemeHost(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
        String scheme = request.getScheme();
        int port = request.getServerPort();
        if (port < 0) {
            port = 80;
        }

        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if (scheme.equals("http") && port != 80 || scheme.equals("https") && port != 443) {
            url.append(':');
            url.append(port);
        }
        return url.toString();
    }
}
