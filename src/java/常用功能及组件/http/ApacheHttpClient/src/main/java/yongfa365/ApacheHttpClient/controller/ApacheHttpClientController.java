package yongfa365.ApacheHttpClient.controller;

import org.apache.http.Consts;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApacheHttpClientController {
    private static final String URI = "/test";

    @GetMapping("apacheHttpClientTest")
    public Object getTest(Object object,HttpServletRequest request) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getRequestSchemeHost(request)+URI+"?object="+object);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String result = httpclient.execute(httpGet, responseHandler);
        return result;
    }

    @PostMapping("apacheHttpClientTest")
    public String postTest(Object object,HttpServletRequest request) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(getRequestSchemeHost(request)+URI);
        List<BasicNameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("object", object.toString()));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String result = httpclient.execute(httpPost, responseHandler);
        return result;
    }

    @DeleteMapping("apacheHttpClientTest")
    public Object deleteTest(Object object,HttpServletRequest request) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(getRequestSchemeHost(request)+URI+"?object="+object);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String result = httpclient.execute(httpDelete, responseHandler);
        return result;
    }

    @PutMapping("apacheHttpClientTest")
    public Object putTest(Object object,HttpServletRequest request) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPut httpPut = new HttpPut(getRequestSchemeHost(request)+URI);
        List<BasicNameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("object", object.toString()));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, Consts.UTF_8);
        httpPut.setEntity(urlEncodedFormEntity);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String result = httpclient.execute(httpPut, responseHandler);
        return result;
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
