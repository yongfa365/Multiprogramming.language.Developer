package yongfa365.lifecycle;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TestInterceptor1 implements HandlerInterceptor {

    /**
     * 在DispatcherServlet之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        System.out.println("************TestInterceptor1 preHandle executed**********");
        return true;
    }

    /**
     * 在controller执行之后的DispatcherServlet之后执行
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
        System.out.println("************TestInterceptor1 postHandle executed**********");
    }

    /**
     * 在页面渲染完成返回给客户端之前执行
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        System.out.println("************TestInterceptor1 afterCompletion executed**********");
    }
}