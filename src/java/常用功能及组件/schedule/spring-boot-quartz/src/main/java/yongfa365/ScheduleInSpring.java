package yongfa365;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//https://www.jianshu.com/p/056281e057b3
//TODO:还没实现
@Slf4j
@EnableScheduling //要启用下
@SpringBootApplication
public class ScheduleInSpring {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleInSpring.class, args);
    }


}


