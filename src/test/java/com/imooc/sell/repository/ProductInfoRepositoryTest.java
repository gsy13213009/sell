package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductInfo;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest() {
        ProductInfo info = new ProductInfo();
        info.setProductId("1231212313");
        info.setProductName("djlajlj");
        info.setProductPrice(new BigDecimal(32));
        info.setProductStock(100);
        info.setCategoryType(11);
        repository.save(info);
    }

    @Test
    public void updateTest() {
        ProductInfo info = repository.findById("1231212313").orElse(null);
        info.setProductStatus(0);
        repository.save(info);
    }

    @Test
    void findByProductStatus() {
        List<ProductInfo> byProductStatus = repository.findByProductStatus(0);
        Assert.assertNotEquals(0, byProductStatus.size());
    }
}
