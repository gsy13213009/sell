package com.imooc.sell.handler;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常
    //http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
//        return new ModelAndView("redirect:"
//            .concat(projectUrlConfig.getWechatOpenAuthorize())
//            .concat("/sell/wechat/qrAuthorize") // QRAuthorize没有做，因为没有账号
//            .concat("?returnUrl=")
//            .concat(projectUrlConfig.getSell())
//            .concat("/sell/seller/login"));
        return new ModelAndView("redirect:"
            .concat(projectUrlConfig.getSell())
            .concat("/sell/seller/login"));
    }
}
