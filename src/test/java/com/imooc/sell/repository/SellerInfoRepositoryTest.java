package com.imooc.sell.repository;

import com.imooc.sell.dataobject.SellerInfo;
import com.imooc.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SellerInfoRepositoryTest {

    @Autowired
    SellerInfoRepository repository;

    @Test
    void save() {
        SellerInfo info = new SellerInfo();
        info.setOpenid("abc");
        info.setUsername("gsy");
        info.setPassword("fdlajfl");
        info.setSellerId(KeyUtil.genUniqueKey());
        SellerInfo save = repository.save(info);
        Assert.assertNotNull(save);
    }

    @Test
    void findByOpenid() {

        SellerInfo abc = repository.findByOpenid("abc");
        Assert.assertNotNull(abc);
    }
}
