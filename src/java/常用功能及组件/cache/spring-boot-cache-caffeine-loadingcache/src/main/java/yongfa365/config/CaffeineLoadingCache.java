package yongfa365.config;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CaffeineLoadingCache {
    /**
     * {@link CaffeineConfig.Settings CaffeineConfig.Settings.XXXXXX}
     *
     * @return
     */
    String name() default "";


}

