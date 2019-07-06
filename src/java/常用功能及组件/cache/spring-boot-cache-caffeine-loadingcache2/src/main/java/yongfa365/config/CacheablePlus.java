package yongfa365.config;

import lombok.ToString;

import java.lang.annotation.*;

/**
 * 会将所有配置拼成一个字符串当cacheName<br/>
 * 也就是配置相同的都会存到同一个缓存集里<br/>
 * 所有时间单位都是：s
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheablePlus {
    /**
     * 可以不写，不重要
     */
    String name() default "";

    long maximumSize() default -1;

    /**
     * 时间单位：s
     */
    long expireAfterWrite() default -1;
    /**
     * 时间单位：s
     */
    long expireAfterAccess() default -1;
    /**
     * 时间单位：s
     */
    long refreshAfterWrite() default -1;

    boolean recordStats() default false;
}

