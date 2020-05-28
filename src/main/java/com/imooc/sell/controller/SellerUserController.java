package com.imooc.sell.controller;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.constants.CookieConstant;
import com.imooc.sell.constants.RedisConstant;
import com.imooc.sell.dataobject.SellerInfo;
import com.imooc.sell.service.impl.SellerServiceImpl;
import com.imooc.sell.utils.CookieUtil;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerServiceImpl sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid, HttpServletResponse response, Map<String, Object> map) {
        // 1. openid去数据库里查询用户
        SellerInfo sellerInfoByOpenid = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfoByOpenid == null) {
            map.put("msg", "登录失败");
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }

        // 2. 设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        // 3. 设置token到cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        // 跳转到订单页面
//        return new ModelAndView("redirect:/sell/seller/order/list"); // 最好不要用相对地址，要用绝对地址
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list"); // 最好不要用相对地址，要用绝对地址

    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        // 1. 从cookie查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            // 2. 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            // 3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }
        map.put("msg", "登出成功");
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

}
