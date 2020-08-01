package com.keith.service.impl;

import com.keith.common.dto.CarDTO;
import com.keith.common.model.ProductInfo;
import com.keith.common.output.ProductInfoOutput;
import com.keith.enums.ResultEnum;
import com.keith.exception.ProductException;
import com.keith.repository.ProductInfoRepository;
import com.keith.service.ProductService;

import com.keith.utils.JsonUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpGoods() {
        return productInfoRepository.findByProductStatus(0);
    }

    @Override
    public List<ProductInfo> findList(List<String> productIds) {
        return productInfoRepository.findByProductIdIn(productIds);
    }

    @Override
    public void decreaseStock(List<CarDTO> carDTOS) {
        //事务操作，对整个list商品扣完库存
        List<ProductInfo> productInfoList = decreaseStockProcess(carDTOS);
        // 发消息MQ，利用lambda8 转成productInfoOutput列表类型
        List<ProductInfoOutput> productInfoOutputList = productInfoList.stream().map(e -> {
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
            BeanUtils.copyProperties(e, productInfoOutput);
            return productInfoOutput;
        }).collect(Collectors.toList());
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutputList));
    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<CarDTO> carDTOS) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        //1. 查找商品
        for (CarDTO carDTO : carDTOS) {
            // 判断商品是否存在
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(carDTO.getProductId());
            if (!productInfoOptional.isPresent()) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            ProductInfo productInfo = productInfoOptional.get();
            // 判断库存是否足够
            Integer result = productInfo.getProductStock() - carDTO.getQuantity();
            if (result < 0) {
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }
}
