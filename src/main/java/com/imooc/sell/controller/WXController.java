package com.imooc.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WXController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code) {
        log.info("进入了auth方法。。code = {}", code);
        RestTemplate restTemplate = new RestTemplate();
        // 需要替换secret
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxb9ba7c646ff42844&secret=xxxxxxx&code=" + code + "&grant_type=authorization_code";
        String forObject = restTemplate.getForObject(url, String.class);
        log.info(forObject);
        //  2020-05-20 10:32:24,067 - {"access_token":"33_Ph6Y-_hxNC_AasnOvdP-UaX7gWXGqa4Cj1s_XabcmMZC35HtcpO61VoW1bea6YAM1lEtcbodG4WLoDuHa-doATVySoL_2lo0sZe9-ZayeqQ","expires_in":7200,"refresh_token":"33_i3eX_sPm7efuezw9NyM5xOYar_rk0S4Hok2SPr5MYbc46ayuAJ6w_IsqbkB3EqoTxDk_75v1JawPqQ-hh1t6x1sHcOWrexPSOT97Vsldpgs","openid":"okynZv2CyB5ZTmnZaYIpgUxoKFPw","scope":"snsapi_userinfo"}
    }

}
