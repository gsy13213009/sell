package com.imooc.sell.dataobject.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.imooc.sell.dataobject.ProductCategory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    void insertByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("category_name", "师兄最不爱");
        map.put("category_type", 15);
        int i = mapper.insertByMap(map);
        Assert.assertEquals(1, i);
    }

    @Test
    void insertByObject() {
        ProductCategory category = new ProductCategory();
        category.setCategoryType(19);
        category.setCategoryName("回复后");
        int i = mapper.insertByObject(category);
        Assert.assertEquals(1, i);
    }

    @Test
    void findByCategoryType() {
        ProductCategory byCategoryType = mapper.findByCategoryType(14);
        Assert.assertNotNull(byCategoryType);
    }

    @Test
    void findByCategoryName() {
        List<ProductCategory> byCategoryType = mapper.findByCategoryName("回复后");
        Assert.assertNotEquals(0, byCategoryType.size());
    }

    @Test
    void updateByCategoryType() {
        int i = mapper.updateByCategoryType("发理发店", 19);
        Assert.assertEquals(1, i);
    }

    @Test
    void updateByCategoryObject() {
        ProductCategory category = new ProductCategory();
        category.setCategoryType(19);
        category.setCategoryName("回复范德萨后");
        int i = mapper.updateByCategoryObject(category);
        Assert.assertEquals(1, i);
    }
    @Test
    void deleteByCategoryType() {
        int i = mapper.deleteByCategoryType(19);
        Assert.assertEquals(1, i);
    }
}
