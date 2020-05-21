package com.imooc.sell.service.impl;

import com.imooc.sell.config.WechatPayConfig;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.utils.JsonUtil;
import com.imooc.sell.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl {

    @Autowired
    private WechatPayConfig wechatPayConfig;

    @Autowired
    private OrderService orderService;

    public PayResponse create(OrderDTO orderDTO) {
        BestPayServiceImpl bestPayService = wechatPayConfig.bestPayService();
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getOrderId());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName("微信点餐订单");
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信支付] request = {}", JsonUtil.toJson(payRequest));
        PayResponse pay = bestPayService.pay(payRequest);
        log.info("[微信支付] response = {}", pay);
        return pay;
    }

    public PayResponse notify(String notifyData) {
        PayResponse payResponse = wechatPayConfig.bestPayService().asyncNotify(notifyData);
        log.info("【微信支付】异步通知 payResponse = {}", payResponse);
        // 1. 验证签名，确定是微信过来的
        // 2. 支付的状态
        // 3. 支付金额是否正确
        // 4. 支付人(下单人==支付人)

        String orderId = payResponse.getOrderId();
        OrderDTO one = orderService.findOne(orderId);
        // 判断订单是否存在
        if (one == null) {
            log.error("[微信支付] 订单不存在 orderId = {}", orderId);
            throw new SellException(-1, "订单不存在");
        }
        // 判断金额是否一致(0.10, 0.1)
        if (!MathUtil.equals(payResponse.getOrderAmount(), one.getOrderAmount().doubleValue())) {
            log.error("[微信支付] 金额校验不通过 orderId = {}，微信金额 = {}, 系统金额 = {}", orderId, payResponse.getOrderAmount(), one.getOrderAmount());
            throw new SellException(-1, "订单金额校验不通过");
        }
        // 修改订单状态
        orderService.paid(one);
        return payResponse;
    }
}
