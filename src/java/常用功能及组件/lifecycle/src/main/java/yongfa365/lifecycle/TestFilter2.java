package yongfa365.lifecycle;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(111)
public class TestFilter2 implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            //在DispatcherServlet之前执行
            System.out.println("############TestFilter2 doFilter before############");
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            //在视图页面返回给客户端之前执行，但是执行顺序在Interceptor之后
            System.out.println("############TestFilter2 doFilter after############");
        }
    }
}