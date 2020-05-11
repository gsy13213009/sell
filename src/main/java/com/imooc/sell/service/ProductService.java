package com.imooc.sell.service;

import com.imooc.sell.dataobject.ProductInfo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductInfo findOne(String productId);

    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 查询所有上架的列表
     */
    List<ProductInfo> findUpAll();

    ProductInfo save(ProductInfo productCategory);

    // 加库存

    // 减库存

}
