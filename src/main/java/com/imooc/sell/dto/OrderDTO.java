package com.imooc.sell.dto;

import com.imooc.sell.dataobject.OrderDetail;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class OrderDTO {

    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus;

    private Integer payStatus;

    private Date createTime;
    private Date updateTime;

    List<OrderDetail> orderDetails;
}
