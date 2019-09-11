package yongfa365.ApacheHttpClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class ApacheHttpClientApplication {
    
    public static void main(String[] args){
        SpringApplication.run(ApacheHttpClientApplication.class,args);
    }

    @GetMapping("test")
    public Object getTest(Object object){
        System.out.println("get:"+object);
        return "get:"+object;
    }

    @PostMapping("test")
    public Object postTest(Object object){
        System.out.println("post:"+object);
        return "post:"+object;
    }

    @DeleteMapping("test")
    public Object deleteTest(Object object){
        System.out.println("delete:"+object);
        return "delete:"+object;
    }

    @PutMapping("test")
    public Object putTest(Object object){
        System.out.println("put:"+object);
        return "put:"+object;
    }


}
