package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.Buyer;
import com.imooc.sell.dataobject.Stock;
import com.imooc.sell.exception.SellException;

import com.imooc.sell.service.BuyerNumService;
import com.imooc.sell.service.RedisLock;
import com.imooc.sell.service.SecKillService;
import com.imooc.sell.service.StockNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecKillServiceImpl implements SecKillService{

    private static final long TIMEOUT = 10 * 1000;

    @Autowired
    private StockNumService stockNumService;

    @Autowired
    private BuyerNumService buyerNumService;

    @Autowired
    private RedisLock redisLock;

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

        //lock
        long time = System.currentTimeMillis() + TIMEOUT;
        boolean lock = redisLock.lock(productId, String.valueOf(time));
        if(!lock)
            throw new SellException(101, "it's already locked!!!!");


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

            stockNumService.update(stock);
        }


        //unlock
        redisLock.unlock(productId, String.valueOf(time));
    }
}
