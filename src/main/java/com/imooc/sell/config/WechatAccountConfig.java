package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Data
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    //wechat_pay
    private String mpAppId;

    //wechat_openid
    private String myAppId;
    private String myAppSecret;



    private String mchId;
    private String mchKey;
    private String keyPath;

    private String notifyUrl;

    private String openAppId;
    private String openAppSecret;

    private Map<String, String> templateId;
    private String sendToUser;
}
