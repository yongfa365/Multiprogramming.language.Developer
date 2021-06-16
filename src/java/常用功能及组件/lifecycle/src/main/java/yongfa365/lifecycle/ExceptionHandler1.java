package yongfa365.lifecycle;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler1 {
    @ExceptionHandler(Exception.class)
    public Object exception(Exception e) {
        var msg = "Error:" + e.getMessage();
        System.out.println(msg);
        return msg;
    }
}
