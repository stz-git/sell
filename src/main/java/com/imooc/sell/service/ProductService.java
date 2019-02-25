package com.imooc.sell.service;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    void increaseStock(List<CarDTO> carDTOList);

    void decreaseStock(List<CarDTO> carDTOList);

    ProductInfo onSale(String productId);

    ProductInfo offSale(String productId);
}
