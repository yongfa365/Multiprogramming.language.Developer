package yongfa365.AutomatedTesting;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandlerContrller {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exception(Exception e) {
        return "自定义错误，不写这个类的话MockMvc没法正常捕捉http错，而变成捕捉程序的Exception了";
    }
}