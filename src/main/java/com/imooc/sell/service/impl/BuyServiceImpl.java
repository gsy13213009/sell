package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BuyServiceImpl {

    @Autowired
    private OrderService orderService;

    public OrderDTO findOrderOne(String openId, String orderId) {
        return checkOrderOwner(openId, orderId);
    }

    public OrderDTO cancelOrder(String openId, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openId, orderId);
        if (orderDTO == null) {
            throw new SellException(-1, "订单不存在");
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openId, String orderId) {
        OrderDTO one = orderService.findOne(orderId);
        if (one == null) {
            return null;
        }
        if (!one.getBuyerOpenid().equalsIgnoreCase(openId)) {
            log.error("[查询订单] openId不一致 openId = {}, orderDTO = {}", openId, one);
            throw new SellException(-1, "该订单不属于当前用户");
        }
        return one;
    }

}
