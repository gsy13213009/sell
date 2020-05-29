package com.imooc.sell.dataobject.mapper;

import com.imooc.sell.dataobject.ProductCategory;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProductCategoryMapper {

    // 通过map的方式写入
    @Insert("insert into product_category(category_name, category_type) values (#{category_name, jdbcType=VARCHAR}, #{category_type, jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    // 通过对象写入
    @Insert("insert into product_category(category_name, category_type) values (#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);

    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
        @Result(column = "category_id", property = "categoryId"),
        @Result(column = "category_name", property = "categoryName"),
        @Result(column = "category_type", property = "categoryType"),
    })
    ProductCategory findByCategoryType(Integer categoryType);

    @Select("select * from product_category where category_name = #{name}")
    @Results({
        @Result(column = "category_id", property = "categoryId"),
        @Result(column = "category_name", property = "categoryName"),
        @Result(column = "category_type", property = "categoryType"),
    })
    List<ProductCategory> findByCategoryName(String name);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByCategoryType(@Param("categoryName") String categoryName, @Param("categoryType") Integer categoryType);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByCategoryObject(ProductCategory category);

    @Delete("delete from product_category where category_type = #{type}")
    int deleteByCategoryType(Integer type);

    // 使用xml的方式，不用注解
    ProductCategory selectByCategoryType(Integer categoryType);
}
