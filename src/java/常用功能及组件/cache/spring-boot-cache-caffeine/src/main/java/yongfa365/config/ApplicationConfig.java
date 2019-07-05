package yongfa365.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

//参考自：https://www.baeldung.com/spring-cache-custom-keygenerator
//实际使用时是一定要启用这个的Configuration的，因为默认的KeyGenerator只考虑方法的参数，而没有考虑类及方法名
//@Configuration
public class ApplicationConfig extends CachingConfigurerSupport {
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) ->
                target.getClass().getSimpleName() + ":"
                        + method.getName() + ":"
                        + StringUtils.arrayToDelimitedString(params, "_");
    }
}
