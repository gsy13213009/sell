package com.imooc.sell.controller;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.impl.PayServiceImpl;
import com.lly835.bestpay.model.PayResponse;
import java.net.URI;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayServiceImpl payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
        @RequestParam("returnUrl") String returnUrl,
        Map<String, Object> map) {
        // 查询订单
        OrderDTO order = orderService.findOne(orderId);
        if (order == null) {
            throw new SellException(-1, "订单不存在");
        }
        // 这里不用payService，是因为没有公众号，拿不到预付订单的信息
//        PayResponse payResponse = payService.create(order);
        PayResponse payResponse = new PayResponse();
        payResponse.setAppId("1212121212");
        payResponse.setOrderId("fdjlsafjlsajl");
        payResponse.setTimeStamp(System.currentTimeMillis() + "");
        payResponse.setNonceStr("fdfjdksljl");
        payResponse.setPackAge("fjdklajl");
        payResponse.setOrderAmount(1d);
        payResponse.setOutTradeNo("fjdkljl");
        payResponse.setPaySign("paysingnjflsjl");
        payResponse.setPayUri(URI.create("http://www.baidu.com"));
        payResponse.setPrePayParams("parmasfldsjl");
        payResponse.setSignType("fdjlalj");
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        // 发起支付
        return new ModelAndView("pay/create", map);
    }

    @GetMapping("/notify")
    public void payNotify() {

    }

}
