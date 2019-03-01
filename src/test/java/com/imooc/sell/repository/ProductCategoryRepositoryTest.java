package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void updateTest() {
        ProductCategory productCategory = repository.findOne(1);
        productCategory.setCategoryName("热销榜网红");
        repository.save(productCategory);
    }

    @Test
    public void test(){
        List<Integer> categoryTypeList = new ArrayList<Integer>();
        categoryTypeList.add(1);
        categoryTypeList.add(2);
        List<ProductCategory> productCategoryList = repository.findByCategoryTypeIn(categoryTypeList);
        System.out.println(productCategoryList);
    }

    @Test
    public void findTest(){
        List<ProductCategory> productCategory = repository.findByCategoryName("火力全开榜");
        System.out.println(productCategory);
    }
}