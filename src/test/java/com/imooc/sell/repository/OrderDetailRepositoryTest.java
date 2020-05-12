package com.imooc.sell.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.imooc.sell.dataobject.OrderDetail;
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
class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("123121");
        orderDetail.setDetailId("121343290900121");
        orderDetail.setProductId("1231212313");
        orderDetail.setProductName("饭到啦放假了");
        orderDetail.setProductPrice(new BigDecimal(22.0));
        orderDetail.setProductQuantity(33);
        repository.save(orderDetail);
    }
    @Test
    void findByOrderId() {
        List<OrderDetail> byOrderId = repository.findByOrderId("123121");
        Assert.assertNotEquals(0, byOrderId.size());
        List<OrderDetail> by2 = repository.findByOrderId("12312aa1");
        Assert.assertEquals(0, by2.size());
    }
}
