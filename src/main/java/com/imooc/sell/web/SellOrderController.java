package com.imooc.sell.web;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
public class SellOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1")Integer page,
                             @RequestParam(value = "size", defaultValue = "10")Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<OrderDTO> list = orderService.findList(pageRequest);

        map.put("orderDTOPage", list);
        return new ModelAndView("order/list", map);
    }
}
