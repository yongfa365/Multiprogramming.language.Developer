package yongfa365.springbootprofiles;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// @Value的配置原则：
// 1. 大小写敏感，得完全匹配
// 2. *.yml得配置，不然初始化时报错，可以指定默认值
@Data
@Component
public class DBNamesConfig {

    @Value("${spring.datasource.hoteldb.username}")
    private String hotelDbUserName;

    @Value("${spring.datasource.flightdb.username}")
    private String flightDbUserName;

    @Value("${spring.datasource.busdb.username:默认值，不配置会报错}")
    private String busDbUserName;
}
