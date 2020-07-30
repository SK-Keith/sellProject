package com.keith.service.impl;

import com.keith.common.dto.CarDTO;
import com.keith.common.model.ProductInfo;
import com.keith.enums.ResultEnum;
import com.keith.exception.ProductException;
import com.keith.repository.ProductInfoRepository;
import com.keith.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;


    @Override
    public List<ProductInfo> findUpGoods() {
        return productInfoRepository.findByProductStatus(0);
    }

    @Override
    public List<ProductInfo> findList(List<String> productIds) {
        return productInfoRepository.findByProductIdIn(productIds);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CarDTO> carDTOS) {
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


        }

    }
}
