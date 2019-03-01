package com.imooc.sell.service;

import com.imooc.sell.dataobject.Stock;

public interface StockNumService {
    Stock findOne(Integer productId);
    void update(Stock stock);
}
