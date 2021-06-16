package yongfa365.lifecycle;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1) //aop也可以指定Order
public class Aop2 {


    @Pointcut("execution(public * yongfa365.lifecycle.HomeController* .*(..))")
    public void point() {
    }

    @Around("point()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        try {
            System.out.println("==============Aop2 start=============");
            return pjp.proceed();
        } finally {
            System.out.println("==============Aop2 end=============");
        }
    }
}