![Filter HandlerInterceptor AOP的顺序及位置](Filter_HnadlerInterceptor_AOP.png)
[此图仿照Baeldung一个图画的](https://www.baeldung.com/spring-mvc-handlerinterceptor-vs-filter)
### 请求正常返回时：
```
############TestFilter2 doFilter before############
############TestFilter1 doFilter before############
************TestInterceptor2 preHandle executed**********
************TestInterceptor1 preHandle executed**********
==============Aop2 start=============
==============Aop1 start=============
yongfa365.lifecycle.HomeController hello
==============Aop1 end=============
==============Aop2 end=============
************TestInterceptor1 postHandle executed**********
************TestInterceptor2 postHandle executed**********
************TestInterceptor1 afterCompletion executed**********
************TestInterceptor2 afterCompletion executed**********
############TestFilter1 doFilter after############
############TestFilter2 doFilter after############
```

### 请求异常时：
```
############TestFilter2 doFilter before############
############TestFilter1 doFilter before############
************TestInterceptor2 preHandle executed**********
************TestInterceptor1 preHandle executed**********
==============Aop2 start=============
==============Aop1 start=============
yongfa365.lifecycle.HomeController ping
==============Aop1 end=============
==============Aop2 end=============
Error:报错演示
************TestInterceptor1 afterCompletion executed**********
************TestInterceptor2 afterCompletion executed**********
############TestFilter1 doFilter after############
############TestFilter2 doFilter after############
```
如果要在AOP加个ThreadLocal的创建及remove，看起来并不合适，因为出错后需要这个信息时已经被remove了


### [廖雪峰画的](https://www.liaoxuefeng.com/wiki/1252599548343744/1347180610715681)：

```
下图虚线框就是Filter2的拦截范围
         │   ▲
         ▼   │
       ┌───────┐
       │Filter1│
       └───────┘
         │   ▲
         ▼   │
       ┌───────┐
┌ ─ ─ ─│Filter2│─ ─ ─ ─ ─ ─ ─ ─ ┐
       └───────┘
│        │   ▲                  │
         ▼   │
│ ┌─────────────────┐           │
  │DispatcherServlet│<───┐
│ └─────────────────┘    │      │
   │              ┌────────────┐
│  │              │ModelAndView││
   │              └────────────┘
│  │                     ▲      │
   │    ┌───────────┐    │
│  ├───>│Controller1│────┤      │
   │    └───────────┘    │
│  │                     │      │
   │    ┌───────────┐    │
│  └───>│Controller2│────┘      │
        └───────────┘
└ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘



下图虚线框就是HandlerInterceptor的拦截范围
       │   ▲
       ▼   │
     ┌───────┐
     │Filter1│
     └───────┘
       │   ▲
       ▼   │
     ┌───────┐
     │Filter2│
     └───────┘
       │   ▲
       ▼   │
┌─────────────────┐
│DispatcherServlet│<───┐
└─────────────────┘    │
 │              ┌────────────┐
 │              │ModelAndView│
 │              └────────────┘
 │ ┌ ─ ─ ─ ─ ─ ─ ─ ─ ┐ ▲
 │    ┌───────────┐    │
 ├─┼─>│Controller1│──┼─┤
 │    └───────────┘    │
 │ │                 │ │
 │    ┌───────────┐    │
 └─┼─>│Controller2│──┼─┘
      └───────────┘
   └ ─ ─ ─ ─ ─ ─ ─ ─ ┘

```
