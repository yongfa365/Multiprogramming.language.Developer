package yongfa365.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yongfa365.Order;

@RestController
@RequestMapping("app")
public class DemoController {

    @PostMapping("getOrder")
    @ApiOperation(value = "查订单")
    public  Order getOrder(Order order)
    {
        return new Order();
    }



    @PostMapping("/app/getOrder")
    @ApiOperation(value = "查订单")
    public  Order getOrder2(Order order)
    {
        return new Order();
    }


}
