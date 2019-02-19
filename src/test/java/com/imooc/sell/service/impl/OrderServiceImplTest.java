package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("zhang san fen");
        orderDTO.setBuyerPhone("15236235896");
        orderDTO.setBuyerOpenid("zhangsanfen123");
        orderDTO.setBuyerAddress("天通苑");

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1");
        orderDetail.setProductQuantity(5);
        orderDTO.setOrderDetailList(Arrays.asList(orderDetail));

        OrderDTO save = orderService.create(orderDTO);
        Assert.assertNotNull(save);
    }

    @Test
    public void findList() {
    }

    @Test
    public void findOne() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}