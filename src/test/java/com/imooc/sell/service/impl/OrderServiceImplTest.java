package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderStatusEnum;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
class OrderServiceImplTest {

    private final static String BUYER_OPEN_ID = "abc123";

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("小糊糊");
        orderDTO.setBuyerAddress("北京海淀区");
        orderDTO.setBuyerPhone("1212321");
        orderDTO.setBuyerOpenid(BUYER_OPEN_ID);

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1390032");
        o1.setProductQuantity(2);
        orderDetails.add(o1);
        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123121ffd2313");
        o2.setProductQuantity(5);
        orderDetails.add(o2);

        orderDTO.setOrderDetails(orderDetails);
        OrderDTO result = orderService.create(orderDTO);
        log.info("[创建订单]：result = {}", result);
    }

    @Test
    void findOne() {
        OrderDTO one = orderService.findOne("1589301857026152981");
        Assert.assertNotNull(one);
        log.info(one.toString());
    }

    @Test
    void findList() {
        PageRequest request = PageRequest.of(0, 1);
        Page<OrderDTO> orderDTOPage = orderService.findList("abc123", request);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    void cancel() {
        OrderDTO one = orderService.findOne("1589301857026152981");
        OrderDTO cancel = orderService.cancel(one);
        log.info(cancel.toString());
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), cancel.getOrderStatus());
    }

    @Test
    void finish() {
        OrderDTO one = orderService.findOne("1589301857026152981");
        orderService.finish(one);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), one.getOrderStatus());
    }

    @Test
    void paid() {
    }
}
