package com.imooc.sell.controller;

import com.imooc.sell.service.impl.SecKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/skill")
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    /**
     * 查询秒杀活动商品信息
     */
    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId) throws Exception {
        return secKillService.querySecKillProductInfo(productId);
    }

    /**
     * 秒杀，没有抢到获得"哎哟喂，xxx"，抢到了返回剩余的库粗量
     */
    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId) throws Exception {
        log.info("skill request , productId: {}", productId);
        secKillService.orderProductMockDiffUser(productId);
        return secKillService.querySecKillProductInfo(productId);
    }
}
