package com.imooc.sell.repository.mapper;

import com.imooc.sell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ProductCategoryMapper {

    @Insert("insert into  product_category(category_name, category_type) values(#{category_name, jdbcType=VARCHAR}, #{category_type, jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    //####### Mapped Statements collection already contains value for com.imooc.sell.repository.mapper.ProductCategoryMapper.insertByMap
//    @Insert("insert into  product_category(category_name, category_type) values(#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER})")
//    int insertByMap(ProductCategory productCategory);

    @Select("select * from product_category where category_type = #{categoryType, jdbcType=INTEGER}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_name", property = "categoryName"),
            @Result(column = "category_type", property = "categoryType")
    })
    ProductCategory findByCategoryType(Integer categoryType);


    @Select("select * from product_category where category_name = #{categoryName, jdbcType=VARCHAR}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_name", property = "categoryName"),
            @Result(column = "category_type", property = "categoryType")
    })
    List<ProductCategory> findByCategoryName(String categoryName);

    @Update("update product_category set category_name=#{categoryName} where category_type=#{categoryType}")
    int updateByCategoryType(@Param("categoryName")String categoryName,
                             @Param("categoryType")Integer categoryType);

    @Update("update product_category set category_name=#{categoryName} where category_type=#{categoryType}")
    int updateByCategory(ProductCategory productCategory);


    ProductCategory SelectByCategoryType(Integer categoryType);

    int insertProductCategory(ProductCategory productCategory);
}
