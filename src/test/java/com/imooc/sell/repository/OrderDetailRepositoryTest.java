package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("2");
        orderDetail.setOrderId("1");
        orderDetail.setProductId("1");
        orderDetail.setProductName("手抓羊肉");
        orderDetail.setProductPrice(new BigDecimal(108));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550490092545&di=bc83d82dc756249717615753be13a311&imgtype=0&src=http%3A%2F%2Fstatics.glshimg.com%2Fforum%2F201807%2F03%2F120134hvgzp1uucj12gr2c.jpg");
        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> list = repository.findByOrderId("1");
        Assert.assertNotEquals(0,list.size());
    }
}