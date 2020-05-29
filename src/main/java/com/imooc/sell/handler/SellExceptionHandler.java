package com.imooc.sell.handler;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.exception.SellerAuthorizeException;
import com.imooc.sell.utils.ResultVOUtil;
import com.imooc.sell.vo.ResultVO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常
    //http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
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

    // 拦截SellException异常，比如创建订单时，商品不存在等
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    // 该异常返回的status
    @ResponseStatus(value = HttpStatus.BAD_GATEWAY) // 可以多创建几个异常类型，每个异常类型返回不同的code
    public ResultVO handlerSellerException(SellException e, HttpServletResponse response) {
//        response.setStatus(500);// 也可以使用该方式修改返回的code
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
