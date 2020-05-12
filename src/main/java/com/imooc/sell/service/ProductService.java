package com.imooc.sell.service;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
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
    void increaseStock(List<CartDTO> cartDTOList);

    // 减库存
    void decreaseStock(List<CartDTO> cartDTOList);

}
