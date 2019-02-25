package com.imooc.sell.web;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Value("${wechat.getOpenid.redirectUrl}")
    private String redirect_url;

    //get code
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {

        //joint request code's url
        String result = wxMpService.oauth2buildAuthorizationUrl(redirect_url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        return "redirect:" + result;
    }

    //get openid
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);//request to get accessToken/openid
        } catch (Exception e) {
            log.info("gain accessToken error");
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        openId = "oTgZpwT7Nk4CKiJFomiQ6NkEuoPU";
        return "redirect:" + returnUrl + "?openid=" + openId;
    }


    @GetMapping("/index")
    public void index(String openid){
        System.out.println(openid);
    }
}
