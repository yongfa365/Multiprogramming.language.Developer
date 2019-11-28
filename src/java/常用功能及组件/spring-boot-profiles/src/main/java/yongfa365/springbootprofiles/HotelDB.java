package yongfa365.springbootprofiles;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@NoArgsConstructor
@Component
// 原则：
// 1. prefix只能是：小写字母，数字，破折线-，英文点隔开各个。
// 2. prefix及field与*.yml里的大小写不一样也能匹配上。
// 3. 默认能匹配上就匹配，类型不对就报错
// @ConfigurationProperties( value = "spring.datasource.hoteldb",ignoreUnknownFields = true,ignoreInvalidFields = false)
@ConfigurationProperties( "spring.datasource.hoteldb")
public class HotelDB {
    private String url;
    private String userName;
    private String passWord;

    private String other;
}
