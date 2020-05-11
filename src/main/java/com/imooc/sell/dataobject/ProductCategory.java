package com.imooc.sell.dataobject;

//import javax.persistence.Table;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 类型
 * //@Table(name = "s_product_category")
 */

@Entity
@DynamicUpdate // 动态更新时间
@Data // lombok 提供，简化toString以及set/get
public class ProductCategory {

    @Id
    // With the generation GenerationType.AUTO hibernate will look for the default hibernate_sequence table , so change generation to IDENTITY as below :
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    private String categoryName;
    private Integer categoryType;

    private Date createTime;
    // 如果写了这个字段，需要DynamicUpdate一起，才能自动修改时间
    private Date updateTime;
}
