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
    public ResultVO list(){

        //1.查询所有上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2.查询要展示的类目
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.拼装数据
        List<ProductVO> productVOList = new ArrayList<>();

        for (ProductCategory category : categories) {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(category,productVO);

            List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();

            //类目中遍历产品对比类目编号的效率实在太低，时间复杂度立马变成O(n^2)
            //但是又不能在这里用类目编号查当前类目下上架的商品，因为尽量不要再for中查询数据库
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
