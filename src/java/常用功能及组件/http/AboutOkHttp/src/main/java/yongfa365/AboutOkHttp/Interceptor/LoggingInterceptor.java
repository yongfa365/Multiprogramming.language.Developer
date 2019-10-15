package yongfa365.AboutOkHttp.Interceptor;

import okhttp3.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LoggingInterceptor implements Interceptor {
    private static Set<String> STRINGS = Set.of("plain", "json", "xml", "html");

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        var sb = new StringBuilder();
        long t1 = System.currentTimeMillis();
        sb.append(request.method()).append(" ").append(request.url()).append(" ");
        sb.append("\n").append(request.headers()).append("\n");

        //        System.out.println(String.format("Sending request %s on %s%n%s",
        //                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.currentTimeMillis();
        //        System.out.println(String.format("Received response for %s in %sms%n%s",
        //                response.request().url(), t2 - t1, response.headers()));

        sb.append(response.protocol()).append(" ").append(response.code()).append(" ").append(response.message());
        sb.append("\n").append(response.headers());
        try {
            var subtype = response.body().contentType().subtype();
            if (STRINGS.contains(subtype)) {
                sb.append("\n").append(response.body().string());
            }
        } catch (Exception e) {
            sb.append("\n").append("没有body");
        }

        System.out.println(sb);
        return response;
    }
}