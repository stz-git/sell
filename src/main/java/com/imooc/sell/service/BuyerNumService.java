package com.imooc.sell.service;

import com.imooc.sell.dataobject.Buyer;

public interface BuyerNumService {

    Buyer findOne(Integer productId);
    void update(Buyer buyer);
}
