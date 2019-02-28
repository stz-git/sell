package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;

public interface PushMessageService {

    //change orderOrderStatus
    void orderStatus(OrderDTO orderDTO);
}
