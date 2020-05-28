package com.imooc.sell.aspect;

import com.imooc.sell.constants.CookieConstant;
import com.imooc.sell.constants.RedisConstant;
import com.imooc.sell.exception.SellerAuthorizeException;
import com.imooc.sell.utils.CookieUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    StringRedisTemplate redisTemplate;


    // 切入的点是Controller里面，以Seller开头的所有类的所有方法，并且排除SellerUserController的所有方法
    @Pointcut("execution(public * com.imooc.sell.controller.Seller*.*(..))" +
        "&& !execution(public * com.imooc.sell.controller.SellerUserController.*(..))")
    public void verify() {
    }

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("[登录校验] Cookie中查询不到token");
            throw new SellerAuthorizeException();
        }
        // 去redis里查
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("[登录校验] Redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}
