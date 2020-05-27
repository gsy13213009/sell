package com.imooc.sell.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.imooc.sell.dataobject.SellerInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SellerServiceImplTest {


    @Autowired
    private SellerServiceImpl sellerService;

    @Test
    void findSellerInfoByOpenid() {
        SellerInfo abc = sellerService.findSellerInfoByOpenid("abc");
        Assert.assertNotNull(abc);
    }
}
