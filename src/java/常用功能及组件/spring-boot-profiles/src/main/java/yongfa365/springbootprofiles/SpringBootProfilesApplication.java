package yongfa365.springbootprofiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootProfilesApplication implements ApplicationRunner {

    @Autowired
    ConnectionStrings connectionStrings;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProfilesApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(connectionStrings.getHotelDbUserName());
        System.out.println(connectionStrings.getFlightDbUserName());
    }
}
