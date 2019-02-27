package com.imooc.sell.web;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.CategoryForm;
import com.imooc.sell.service.CategoryService;
import com.lly835.bestpay.rest.type.Get;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map){
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false)Integer categoryId,
                              Map<String, Object> map){
        if(!StringUtils.isEmpty(categoryId)) {
            ProductCategory category = categoryService.findOne(categoryId);
            map.put("category", category);
        }
        return new ModelAndView("category/index", map);
    }

    @PostMapping("/save")
    public ModelAndView save(CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }

        ProductCategory productCategory = new ProductCategory();
        try{
            if (!StringUtils.isEmpty(categoryForm.getCategoryId()))
                productCategory = categoryService.findOne(categoryForm.getCategoryId());
            //update
//            ProductCategory productCategory = new ProductCategory();//##### createTime = updateTime
            BeanUtils.copyProperties(categoryForm, productCategory);
            categoryService.save(productCategory);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/success", map);
        }
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", map);
    }
}
