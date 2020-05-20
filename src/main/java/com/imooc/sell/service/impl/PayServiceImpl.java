package com.imooc.sell.service.impl;

import com.imooc.sell.config.WechatPayConfig;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.utils.JsonUtil;
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

    public void create(OrderDTO orderDTO) {
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
    }
}
