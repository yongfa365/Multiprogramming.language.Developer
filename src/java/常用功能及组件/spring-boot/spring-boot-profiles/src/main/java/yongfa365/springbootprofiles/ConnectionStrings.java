package yongfa365.springbootprofiles;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@Component
public class ConnectionStrings {

    @Value("${spring.datasource.hoteldb.username}")
    private String hotelDbUserName;

    @Value("${spring.datasource.flightdb.username}")
    private String flightDbUserName;
}
