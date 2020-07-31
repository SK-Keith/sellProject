package com.keith.service.impl;

import com.keith.client.ProductClient;
import com.keith.common.dto.CarDTO;
import com.keith.common.dto.OrderDTO;
import com.keith.common.model.OrderDetail;
import com.keith.common.model.OrderMaster;
import com.keith.common.model.ProductInfo;
import com.keith.enums.OrderStatusEnum;
import com.keith.enums.PayStatusEnum;
import com.keith.repository.OrderDetailRepository;
import com.keith.repository.OrderMasterRepository;
import com.keith.service.OrderService;
import com.keith.utils.KeyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtils.getUniqueKey();

        //根据前端传的商品信息id获取id集合
        List<String> productList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        // 查询商品信息（调商品服务）
        List<ProductInfo> productInfos = productClient.findByProductId(productList);
        // 计算总价
        BigDecimal orderAcount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfo productInfo : productInfos) {
                    if (orderDetail.getProductId().equals(productInfo.getProductId())) {
                        orderAcount = productInfo.getProductPrice()
                                .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                                .add(orderAcount);
                        BeanUtils.copyProperties(productInfo, orderDetail);
                        orderDetail.setOrderId(orderId);
                        orderDetail.setDetailId(KeyUtils.getUniqueKey());
                        // 订单详情入库
                        orderDetailRepository.save(orderDetail);
                    }
            }
        }
        // 扣库存（调商品服务）
        List<CarDTO> carDTOS = orderDTO.getOrderDetailList().stream()
                .map(e -> new CarDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(carDTOS);

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAcount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
//        order.setBuyerName(orderDTO.getBuyerName());
//        order.setBuyerPhone(orderDTO.getBuyerPhone());
//        order.setOrder

        return orderDTO;
    }
}
