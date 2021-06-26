![Filter HandlerInterceptor AOP的顺序及位置](Filter_HnadlerInterceptor_AOP.png)
[此图仿照Baeldung一个图画的](https://www.baeldung.com/spring-mvc-handlerinterceptor-vs-filter)
![Filter HandlerInterceptor AOP的顺序及位置](http://www.plantuml.com/plantuml/png/ZP7FYzD05CVlyrSyqbiWi3FY7iAoRBT9S45H3FNYUfYcptReT2PE9jljpHzU18-2eDuyUnFfmVz6M_qpdCbGsug2fxnyt-VzcEyxQomtjfrBCaK-HWFHQ0bBhIQF-OtZcm3iYD7aw3XXnn6nmag4aqfBRQ1dS7mQnuMG5istfyykbFjMM5lj__ftx_ruz_BdwnVhzv_Zc3GpeMfk-1n6l9fDZ6xLU13Qv7b-dWtsY2bNuuKHrg82QrhSI_MDqOjGDvAk4U6LTV6mGGEyWI50Rs8G5I6Ejw8IDLSMmehK4mM5NePObxFbkMpnBnZhC7Q8aP6-XgYiZL0JkFDW447ljfXCRPUijB96IuWoqTJSJj6KQAuasiYtoig38H3TSi-JQ2xLdc50E_Q2_dx0VzImKCF-KPDVUqveTQb2kUdud49tkjtQxbGGvlTzRebIwiLXceKqswL7_eBSB24Q173RV-0ShbjJIoIenk2C8uJmrcftAd75BO9MX0m183dzynmfnF5zVD9YO-EO53Gmx90XJl1wLgOaAtteVGiMfCI5dS9sIi3xTBjxIbpSIMnF2jHk-tJpwUDwjTgy_U9p9qdYSYd9gGiVUlyU9Tehcm-lrb-V1_3xwitPrlMjvsxyVaLE_NJVpattkuMGkZ2e5yo_AeHEBGBWtre4e7304498Ws5DhLM3ph4pvtWxbxy0)
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
