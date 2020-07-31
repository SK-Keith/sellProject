package com.keith.service;


import com.keith.common.dto.OrderDTO;

public interface OrderService {
    // 创建订单
    OrderDTO create(OrderDTO orderDTO);
}
