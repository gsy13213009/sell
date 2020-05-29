package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PushMessage {

    @Autowired
    private WxMpService wxMpService;


    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        // 模板id，从公众号后台获取
        wxMpTemplateMessage.setTemplateId("30DSnNGaT1vxDK15iDf1nNzg8m103gN_ZGiKur4MQgc");
        // 用户关注该公众号的openid
        wxMpTemplateMessage.setToUser("okynZv2CyB5ZTmnZaYIpgUxoKFPw");
        // 字段根据模板来定
        List<WxMpTemplateData> data = Arrays.asList(
            new WxMpTemplateData("first", "亲，记得收货哦"),
            new WxMpTemplateData("keyword1", "郭思义"),
            new WxMpTemplateData("keyword2", "18810923902"),
            new WxMpTemplateData("keyword3", "明天"),
            new WxMpTemplateData("keyword4", "少儿班"),
            new WxMpTemplateData("remark", "欢迎再次光临")
        );

        wxMpTemplateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            log.error("[发送消息] 发送消息失败 {}", e);
        }
    }
}
