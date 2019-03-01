package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.Stock;
import com.imooc.sell.repository.StockRepository;
import com.imooc.sell.service.StockNumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StockNumServiceImpl implements StockNumService{

    private static int COUNT = 0;

    @Autowired
    private StockRepository repository;

    @Override
    public Stock findOne(Integer productId) {
        return repository.findOne(productId);
    }

    @Override
    public void update(Stock stock) {
        log.info("stock update count: {}, stockNum(NOW!!!): {}", COUNT++, stock.getStockNum());
        repository.save(stock);
    }
}
