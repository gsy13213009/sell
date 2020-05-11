package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductCategory;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
        ProductCategory productCategory = repository.findById(1).orElse(null);
        System.out.println(productCategory.toString());
    }


    @Test
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(11);
        repository.save(productCategory);
    }

    @Test
    @Transactional // 做完后回滚，也就是测试数据不会被写到数据库里面
    public void update() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("男生");
        productCategory.setCategoryType(11);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(2, 3, 4, 11);
        List<ProductCategory> byCategoryTypeIn = repository.findByCategoryTypeIn(list);
        log.info(byCategoryTypeIn.size() + "");
        Assert.assertNotEquals(0, byCategoryTypeIn.size());
    }
}
