package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "project_url")
@Data
public class ProjectUrlConfig {

    public String wechatMpAuthorize;
    public String wechatOpenAuthorize;
    public String login;
    public String sell;
}
