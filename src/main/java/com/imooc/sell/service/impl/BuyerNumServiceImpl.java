package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.Buyer;
import com.imooc.sell.repository.BuyerRepository;
import com.imooc.sell.service.BuyerNumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerNumServiceImpl implements BuyerNumService{

    private static int COUNT = 0;

    @Autowired
    private BuyerRepository repository;

    @Override
    public Buyer findOne(Integer productId) {
        return repository.findOne(productId);
    }

    @Override
    public void update(Buyer buyer) {
        log.info("buyer update count: {}, buyerNum(NOW!!): {}", COUNT++, buyer.getBuyerNum());
        repository.save(buyer);
    }
}
