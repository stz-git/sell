package com.imooc.sell.service.impl;

import com.imooc.sell.converter.OrderMaster2OrderDTOConverter;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CarDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.OrderDetailRepository;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PayService payService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1.查询商品
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null)
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            //2.计算总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }

        //3.写入数据库
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMasterRepository.save(orderMaster);

        //4.扣库存
        List<CarDTO> carDTOList = orderDetailList.stream()
                .map(e -> new CarDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(carDTOList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> page = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        Page<OrderDTO> result = new PageImpl<OrderDTO>(OrderMaster2OrderDTOConverter.convert(page.getContent()), pageable, page.getTotalElements());
        return result;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null)
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
        if (CollectionUtils.isEmpty(orderDetailList))
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        //1.judge order's status
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.update order's status(transaction rollback)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //3.stock +1
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CarDTO> carDTOList = orderDTO.getOrderDetailList()
                .stream()
                .map(e -> new CarDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(carDTOList);

        //4.refund
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            payService.refoud(orderDTO);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {

        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);

        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //1.judge order's status
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.judge order's pay status
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //3.update order's pay status
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return null;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> page = orderMasterRepository.findAll(pageable);
        Page<OrderDTO> result = new PageImpl<OrderDTO>(OrderMaster2OrderDTOConverter.convert(page.getContent()), pageable, page.getTotalElements());
        return result;
    }
}
