package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
