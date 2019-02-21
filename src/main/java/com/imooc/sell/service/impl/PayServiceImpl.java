package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.imooc.sell.util.JsonUtil;
import com.imooc.sell.util.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService{

    private static final String ORDER_NAME = "wechat_food_order";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    //create pre_order
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("payResponse={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1.check sign
        //2.check order's status[SUCCESS]
        //3.checkout orderAmount
        //4.openid is singleton?

        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("payResponse={}", payResponse);

        //change order's payStatus to paid
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        if(orderDTO == null)
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        if(!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue()))
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);

        orderService.paid(orderDTO);

        return payResponse;
    }

    @Override
    public RefundResponse refoud(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("refundRequest={}",JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("refundResponse={}",JsonUtil.toJson(refundResponse));
        return refundResponse;
    }
}
