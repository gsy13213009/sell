package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderMaster;
import java.math.BigDecimal;
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
class OrderMasterRepositoryTest {

    static final String open_id = "12312ff1";
    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(open_id);
        orderMaster.setBuyerName("fdafas");
        orderMaster.setBuyerAddress("北京a海淀aa区");
        orderMaster.setBuyerOpenid("abc123");
        orderMaster.setBuyerPhone("1881fff092");
        orderMaster.setOrderAmount(new BigDecimal(3.00));
        repository.save(orderMaster);
    }

    @Test
    void findByBuyerOpenid() {
        Page<OrderMaster> byBuyerOpenid = repository.findByBuyerOpenid(open_id, PageRequest.of(0, 1));
        Assert.assertNotEquals(0, byBuyerOpenid.getSize());
    }
}
