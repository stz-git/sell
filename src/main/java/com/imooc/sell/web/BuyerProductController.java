package com.imooc.sell.web;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.util.ResultUtil;
import com.imooc.sell.vo.ProductInfoVO;
import com.imooc.sell.vo.ProductVO;
import com.imooc.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "product", key = "123", condition = "", unless = "#result != 0")
    public ResultVO list(){

        int result = 1;

        //1.get up product
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2.get category
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.joint result
        List<ProductVO> productVOList = new ArrayList<>();

        for (ProductCategory category : categories) {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(category,productVO);

            List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();

            for (ProductInfo productInfo : productInfoList) {
                if(productInfo.getCategoryType().equals(category.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultUtil.success(productVOList);
    }
}
