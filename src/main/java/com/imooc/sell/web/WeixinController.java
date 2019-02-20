package com.imooc.sell.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code")String code, @RequestParam("state")String state) {
        log.info("auth...");
        System.out.println(code);


    }

    public static void main(String[] args) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxf14bf9b7d6a0f4d0&secret=c9fa4d34f8d7a54f7575e5926ae590d1&code=071FLxek27KvbE01mIfk24rrek2FLxep&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String str = restTemplate.getForObject(url, String.class);
        System.out.println(str);
    }
}
