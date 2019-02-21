package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<OrderDTO> page = orderService.findList("abc123", pageRequest);
        System.out.println(page.getContent());
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne("1");
        System.out.println(orderDTO);
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne("1550562471973203311");
        orderService.cancel(orderDTO);
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne("1550562471973203311");
        orderService.paid(orderDTO);
    }
}