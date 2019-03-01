package com.imooc.sell.repository.mapper;

import com.imooc.sell.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMapTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("category_name", "火力全开榜");
        map.put("category_type", "5");
        int index = mapper.insertByMap(map);
        log.info("index={}", index);
    }

    @Test
    public void findTest(){
        ProductCategory productCategory = mapper.findByCategoryType(4);
        System.out.println(productCategory);
    }

    @Test
    public void findByCategoryNameTest(){
        List<ProductCategory> productCategory = mapper.findByCategoryName("火力全开榜");
        System.out.println(productCategory);
    }

    @Test
    public void updateTest(){
        int index = mapper.updateByCategoryType("流行榜", 4);
        System.out.println(index);
    }

    @Test
    public void updateByCategoryTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("小苏推荐");
        productCategory.setCategoryType(5);
        int index = mapper.updateByCategory(productCategory);
    }

    @Test
    public void selectTest(){
        ProductCategory productCategory = mapper.SelectByCategoryType(4);
        System.out.println(productCategory);
    }

    @Test
    public void insertTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("火力全开榜");
        productCategory.setCategoryType(6);
        mapper.insertProductCategory(productCategory);
        //########### get genKey ###############
        System.out.println(productCategory.getCategoryId());
    }
}