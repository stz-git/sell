package com.imooc.sell.web;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
                                     @RequestParam("returnUrl") String returnUrl) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null)
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);

        PayResponse payResponse = payService.create(orderDTO);

        HashMap<String, Object> map = new HashMap<>();
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", map);
    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        /*<xml >
            <appid ><![CDATA[wxd898fcb01713c658]]></appid >
            <bank_type ><![CDATA[COMM_DEBIT]]></bank_type >
            <cash_fee ><![CDATA[1]]></cash_fee >
            <fee_type ><![CDATA[CNY]]></fee_type >
            <is_subscribe ><![CDATA[Y]]></is_subscribe >
            <mch_id ><![CDATA[1483469312]]></mch_id >
            <nonce_str ><![CDATA[BaPpw3JYFoW2lbNf]]></nonce_str >
            <openid ><![CDATA[oTgZpwT7Nk4CKiJFomiQ6NkEuoPU]]></openid >
            <out_trade_no ><![CDATA[1550562471973203366]]></out_trade_no >
            <result_code ><![CDATA[SUCCESS]]></result_code >
            <return_code ><![CDATA[SUCCESS]]></return_code >
            <sign ><![CDATA[742 B95C199546C7903809FFE8BAFCD00]]></sign >
            <time_end ><![CDATA[20190221134755]]></time_end >
            <total_fee > 1 </total_fee >
            <trade_type ><![CDATA[JSAPI]]></trade_type >
            <transaction_id ><![CDATA[4200000240201902213719540891]]></transaction_id >
        </xml >*/
        payService.notify(notifyData);
        return new ModelAndView("pay/success");
    }
}
