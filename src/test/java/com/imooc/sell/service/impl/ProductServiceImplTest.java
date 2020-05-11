package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.enums.ProductStatusEnum;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    void findOne() {
        ProductInfo one = productService.findOne("1231212313");
        Assert.assertEquals(new Integer(100), one.getProductStock());
    }

    @Test
    void findAll() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<ProductInfo> all = productService.findAll(pageRequest);
        System.out.println(all.getTotalElements());
    }

    @Test
    void findUpAll() {
        List<ProductInfo> upAll = productService.findUpAll();
        Assert.assertNotEquals(0, upAll.size());
    }

    @Test
    void save() {
        ProductInfo info = new ProductInfo();
        info.setProductId("123121ffd2313");
        info.setProductName("djladsafdsafdsafjlj");
        info.setProductPrice(new BigDecimal(32));
        info.setProductStock(100);
        info.setCategoryType(11);
        info.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productService.save(info);
    }
}
