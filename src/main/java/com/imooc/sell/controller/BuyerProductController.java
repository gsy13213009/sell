package com.imooc.sell.controller;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.utils.ResultVOUtil;
import com.imooc.sell.vo.ProductInfoVO;
import com.imooc.sell.vo.ProductVO;
import com.imooc.sell.vo.ResultVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    // redis的缓存，此注解会将resultVO缓存到redis里面，再次调用词接口时，就不会再查询数据库
    // 直接从redis的缓存里面返回给前端
    // key 可以指定为参数
    // condition 指当满足条件时，才缓存
    // unless，指，满足条件时，不缓存，也就是只有code为0才缓存
    @Cacheable(cacheNames = "product", key = "#sellerId", condition = "#sellerId.length() > 3", unless = "#result.code != 0")

    // @CacheEvict(cacheNames = "product", key = "123") // 将此注解放到save list 的接口上
    // 保证当修改list后，删除redis的缓存，此时再次访问list接口便会从数据库读取

    // @CachePut(cacheNames = "product", key = "123") // 此注解放到save list接口上时，会将
    // save list接口的返回值写到redis里面，注意，save list的返回值需要是ResultVO，不能是其他
    public ResultVO list(@RequestParam(value = "sellerId", defaultValue = "1") String sellerId) {
        // 1. 查询所有上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

//          2. 类目查询（一次查询，不能for循环商品去查询）
//          List<Integer> categoryTypeList = new ArrayList<>();
//          // 传统方法
//          for (ProductInfo productInfo : productInfoList) {
//              categoryTypeList.add(productInfo.getCategoryType());
//          }
        // 精简方法(java8)
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        List<ProductVO> productVOList = new ArrayList<>();
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        // 3. 数据拼装
        for (final ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (final ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    // 拷贝属性到productInfoVO对象
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }

}
