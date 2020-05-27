package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.SellerInfo;
import com.imooc.sell.repository.SellerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl {

    @Autowired
    private SellerInfoRepository repository;

    SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }

}
