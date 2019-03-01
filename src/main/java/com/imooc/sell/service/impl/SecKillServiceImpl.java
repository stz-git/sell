package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.Buyer;
import com.imooc.sell.dataobject.Stock;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.BuyerRepository;
import com.imooc.sell.repository.StockRepository;
import com.imooc.sell.service.BuyerNumService;
import com.imooc.sell.service.SecKillService;
import com.imooc.sell.service.StockNumService;
import com.imooc.sell.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecKillServiceImpl implements SecKillService{

    @Autowired
    private StockNumService stockNumService;

    @Autowired
    private BuyerNumService buyerNumService;

    private String queryMap(String productId)
    {
        return "国庆活动，皮蛋粥特价，限量100000份\n 还剩："
                + stockNumService.findOne(1).getStockNum()+" 份\n"
                +" 该商品成功下单用户数目："
                +  buyerNumService.findOne(1).getBuyerNum() +" 人" ;
    }

    @Override
    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    @Override
    public void orderProductMockDiffUser(String productId) {
        //1.query stock
        Stock stock = stockNumService.findOne(1);
        int stockNum = stock.getStockNum();
        if(stockNum == 0)
            throw new SellException(-1, "end!!!!");
        else{
            //2.buy
            Buyer buyer = buyerNumService.findOne(1);
            buyer.setBuyerNum(buyer.getBuyerNum()+1);
            buyerNumService.update(buyer);

            //3.decreaseStock
            stockNum = stockNum - 1;
            stock.setStockNum(stockNum);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
/*
            buyer update count: 117, buyerNum(NOW!!): 51

            2019-03-01 18:52:20.095  INFO 7880 --- [nio-8080-exec-9] c.i.s.service.impl.StockNumServiceImpl   : stock update count: 105, stockNum(NOW!!!): 99992
            2019-03-01 18:52:20.100  INFO 7880 --- [io-8080-exec-52] c.i.s.service.impl.StockNumServiceImpl   : stock update count: 106, stockNum(NOW!!!): 99992
            2019-03-01 18:52:20.111  INFO 7880 --- [io-8080-exec-26] c.i.s.service.impl.StockNumServiceImpl   : stock update count: 107, stockNum(NOW!!!): 99992
            2019-03-01 18:52:20.111  INFO 7880 --- [nio-8080-exec-7] c.i.s.service.impl.StockNumServiceImpl   : stock update count: 107, stockNum(NOW!!!): 99992

            2019-03-01 18:52:20.259  INFO 7880 --- [io-8080-exec-13] c.i.s.service.impl.BuyerNumServiceImpl   : buyer update count: 118, buyerNum(NOW!!): 52
            */

            stockNumService.update(stock);
        }
    }
}
