package yongfa365.RestTemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String URI = "/test";

    @GetMapping("restTemplateTest")
    public Object getTest(Object object,HttpServletRequest request){
        String resultObject = restTemplate.getForObject(getRequestSchemeHost(request)+ URI +"?object="+object, String.class);
        return resultObject;
    }

    @PostMapping("restTemplateTest")
    public ResponseEntity<String> postTest(Object object,HttpServletRequest request){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,Object> map=new LinkedMultiValueMap<>();
        map.add("object",object);
        HttpEntity<MultiValueMap<String,Object>> reqest=new HttpEntity<>(map,headers);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(getRequestSchemeHost(request) + URI, reqest, String.class);
        return stringResponseEntity;
    }

    @DeleteMapping("restTemplateTest")
    public Object deleteTest(Object object,HttpServletRequest request){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("object",object);
        restTemplate.delete(getRequestSchemeHost(request)+ URI +"?object="+object);
        return "delete:"+object;
    }

    @PutMapping("restTemplateTest")
    public Object putTest(Object object,HttpServletRequest request){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,Object> map=new LinkedMultiValueMap<>();
        map.add("object",object);
        HttpEntity<MultiValueMap<String,Object>> reqest=new HttpEntity<>(map,headers);
        restTemplate.put(getRequestSchemeHost(request) + URI, reqest, String.class);
        return "put:"+object;
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
