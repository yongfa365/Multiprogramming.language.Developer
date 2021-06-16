package yongfa365.lifecycle;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        System.out.println(this.getClass().getName() + " hello");
        return "hello";
    }

    @GetMapping("/ping")
    public String ping() {
        System.out.println(this.getClass().getName() + " ping");
        throw new RuntimeException("报错演示");
    }

}
