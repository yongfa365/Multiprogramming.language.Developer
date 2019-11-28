package yongfa365.springbootprofiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootProfilesApplication implements ApplicationRunner {

    @Autowired
    DBNamesConfig DBNamesConfig;

    @Autowired
    HotelDB hotelDB;

    @Autowired
    ConnectionStrings connectionStrings;


    @Autowired
    @Qualifier("hotelDBFromFlightDBConfig")
    HotelDB hotelDBFromFlightDBConfig;

    // 即便类已经指定了ConfigurationProperties，依然可以通过这种方式再给他指定个，当然最终产生两个Bean
    @ConfigurationProperties("spring.datasource.flightdb")
    @Bean("hotelDBFromFlightDBConfig")
    HotelDB xxx() {
        return new HotelDB();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProfilesApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(DBNamesConfig);

        System.out.println(hotelDB);

        System.out.println(connectionStrings);

        System.out.println(hotelDBFromFlightDBConfig);
    }
}
