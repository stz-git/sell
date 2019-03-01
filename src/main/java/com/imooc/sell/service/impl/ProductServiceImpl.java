package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CarDTO;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.ProductInfoRepository;
import com.imooc.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CarDTO> carDTOList) {
        for (CarDTO carDTO : carDTOList) {
            ProductInfo productInfo = findOne(carDTO.getProductId());
            if(productInfo == null)
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            productInfo.setProductStock(carDTO.getProductQuantity() + productInfo.getProductStock());
            save(productInfo);
        }
    }

    //TODO
    //multiThread-->over_sale
    @Override
    @Transactional
    public void decreaseStock(List<CarDTO> carDTOList) {
        for (CarDTO carDTO : carDTOList) {
            ProductInfo productInfo = findOne(carDTO.getProductId());
            if(productInfo == null)
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            Integer stock = productInfo.getProductStock() - carDTO.getProductQuantity();
            if(stock < 0)
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            productInfo.setProductStock(stock);
            save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = findOne(productId);
        if(productInfo == null)
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);

        //judge product's onsale status
        if(productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode()))
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);

        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = findOne(productId);
        if(productInfo == null)
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);

        //judge product's onsale status
        if(productInfo.getProductStatus().equals(ProductStatusEnum.DOWN.getCode()))
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);

        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}
