package yongfa365.lifecycle;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Aop1 {


    @Pointcut("execution(public * yongfa365.lifecycle.HomeController* .*(..))")
    public void point() {
    }

    @Around("point()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        try {
            System.out.println("==============Aop1 start=============");
            return pjp.proceed();
        } finally {
            System.out.println("==============Aop1 end=============");
        }
    }
}