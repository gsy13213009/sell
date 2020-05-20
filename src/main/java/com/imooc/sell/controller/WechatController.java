package com.imooc.sell.controller;

import com.imooc.sell.config.WechatMpConfig;
import com.imooc.sell.exception.SellException;
import java.net.URLEncoder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts.OAuth2Scope;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // 用Controller，才会重定向
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WechatMpConfig wechatMpConfig;

    @GetMapping("/authorize")
    public String authorize(@RequestParam(value = "returnUrl", defaultValue = "") String returnUrl) {
        // 1. 配置(在WxMpService里面设置了)
        // 2. 调用方法
        String url = "http://9tf4gc.natappfree.cc/sell/wechat/userInfo";
        String result = wechatMpConfig.wxMpService().oauth2buildAuthorizationUrl(url, OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        log.info("[微信网页授权] 获取code result = {}", result);
        return "redirect:" + result;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {
        log.info("[userInfo] 进入到userInfo, code = {}, returnUrl = {}", code, returnUrl);
        // 拿到code后，去要获取openId
        WxMpOAuth2AccessToken auth2AccessToken;
        try {
            auth2AccessToken = wechatMpConfig.wxMpService().oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("[微信网页授权失败] {}", e.toString());
            throw new SellException(-1, "微信授权失败");
        }
        String openId = auth2AccessToken.getOpenId();
        // 带上openid请求目标地址
        return "redirect:" + returnUrl + "?openid=" + openId;
    }
}
