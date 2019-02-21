package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    //wechat_pay
    private String mpAppId;

    //wechat_openid
    private String myAppId;
    private String mpAppSecret;



    private String mchId;
    private String mchKey;
    private String keyPath;

    private String notifyUrl;
}
