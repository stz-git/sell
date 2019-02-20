package com.imooc.sell.web;

import com.imooc.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public String createPrePay(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl")String returnUrl){
        //pre_pay gain prepayid
        return null;
    }
}
