package com.imooc.sell.web;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView createPrePay(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl")String returnUrl){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null)
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);

        PayResponse payResponse = payService.create(orderDTO);

        HashMap<String, Object> map = new HashMap<>();
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("pay/create",map);
    }

    @GetMapping("/notify")
    public void notifySync(){

    }
}
