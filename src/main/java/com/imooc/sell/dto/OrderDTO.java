package com.imooc.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.utils.serializer.Date2LogSerializer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
//@JsonSerialize(include = Inclusion.NON_NULL) // 已经废弃了
//@JsonInclude(Include.NON_NULL) // 不返回null值的对象，可以用全局配置
public class OrderDTO {

    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus;

    private Integer payStatus;

    @JsonSerialize(using = Date2LogSerializer.class)
    private Date createTime;
    // 使用JsonSerialize修改数据字段的结果
    @JsonSerialize(using = Date2LogSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetails = new ArrayList<>(); // 如果有默认值，则返回值会是[]
}
