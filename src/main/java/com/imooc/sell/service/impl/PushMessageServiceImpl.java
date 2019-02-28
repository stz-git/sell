package com.imooc.sell.service.impl;

import com.imooc.sell.config.WechatAccountConfig;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        wxMpTemplateMessage.setToUser(accountConfig.getSendToUser());
        List<WxMpTemplateData> wxMpTemplateData = Arrays.asList(
                new WxMpTemplateData("first", "Honey!!"),
                new WxMpTemplateData("keyword1", "Nice to meet you!!!"),
                new WxMpTemplateData("keyword2", "18526358569"),
                new WxMpTemplateData("keyword3", orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMsg()),
                new WxMpTemplateData("keyword5", "￥" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark", "see you again !!!")
        );
        wxMpTemplateMessage.setData(wxMpTemplateData);
        try{
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        }catch (WxErrorException e){
            log.error("wechat send message error! error={}", e);
        }
    }
}
