package yongfa365.springbootprofiles;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("spring.datasource")
public class ConnectionStrings {
    private DBConn FlightDB;
    private DBConn HotelDB;

    @Data
    public static class DBConn {
        private String url;
        private String userName;
        private String passWord;
    }
}

