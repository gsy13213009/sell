package com.imooc.sell.dataobject;

//import javax.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
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
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = -9164614871839181961L;
    @Id
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productStock;
    private String productDescription;
    private String productIcon;
    private Integer productStatus;
    private Integer categoryType;
}
