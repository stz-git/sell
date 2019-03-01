package com.imooc.sell.web;

import com.imooc.sell.config.ProjectUrlConfig;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //get code
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {

        //joint request code's url
        String result = wxMpService.oauth2buildAuthorizationUrl(projectUrlConfig.wechatMpAuthorize, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
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


    @GetMapping("/qrAuthorize")
    public String qrAuthorize(){
//        String result = wxOpenService.buildQrConnectUrl(projectUrlConfig.wechatOpenAuthorize, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN, URLEncoder.encode(returnUrl));

        String myOpenid = "oTgZpwT7Nk4CKiJFomiQ6NkEuoPU";
        String myRedirectUrl = URLEncoder.encode(projectUrlConfig.wechatOpenAuthorize);

        String result = "https://open.weixin.qq.com/connect/qrconnect?appid=wx6ad144e54af67d87&redirect_uri=http%3A%2F%2Fsell.springboot.cn%2Fsell%2Fqr%2F"
                +myOpenid+"&response_type=code&scope=snsapi_login&state="
                +myRedirectUrl;
        return "redirect:" + result;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code){

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();

        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);//request to get accessToken/openid
        } catch (Exception e) {
            log.info("gain accessToken error");
        }

        log.info("wxMpOAuth2AccessToken={}", wxMpOAuth2AccessToken);
//        String openId = wxMpOAuth2AccessToken.getOpenId();
        String openId = "tianyu24";
        return "redirect:" + projectUrlConfig.login + "?openid=" + openId;
    }
}
