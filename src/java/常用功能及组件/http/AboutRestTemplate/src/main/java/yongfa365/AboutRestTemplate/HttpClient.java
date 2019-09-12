package yongfa365.AboutRestTemplate;

import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class HttpClient {

    /**
     * https://docs.spring.io/spring/docs/5.0.4.BUILD-SNAPSHOT/javadoc-api/org/springframework/web/client/RestTemplate.html
     */
    private volatile static RestTemplate restTemplate;

    public static RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            synchronized (HttpClient.class) {
                if (restTemplate == null) {
                    //https://docs.spring.io/spring/docs/5.0.4.BUILD-SNAPSHOT/javadoc-api/org/springframework/http/client/SimpleClientHttpRequestFactory.html
                    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
                    factory.setBufferRequestBody(false);//Indicate whether this request factory should buffer the request body internally.
                    factory.setChunkSize(4096);//Set the number of bytes to write in each chunk when not buffering request bodies locally.
                    factory.setConnectTimeout(15000);//Set the underlying URLConnection's connect timeout (in milliseconds).
                    factory.setOutputStreaming(true);//Set if the underlying URLConnection can be set to 'output streaming' mode.
                    factory.setProxy(null);//Set the Proxy to use for this request factory.
                    factory.setReadTimeout(5000);//Set the underlying URLConnection's read timeout (in milliseconds).
                    factory.setTaskExecutor(new SimpleAsyncTaskExecutor());//Set the task executor for this request factory.
                    restTemplate = new RestTemplate(factory);
                }
            }
        }
        return restTemplate;
    }
}
