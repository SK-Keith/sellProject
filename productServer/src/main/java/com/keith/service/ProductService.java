package com.keith.service;


import com.keith.common.dto.CarDTO;
import com.keith.common.model.ProductInfo;

import java.util.List;

public interface ProductService {

    List<ProductInfo> findUpGoods();

    // 查询商品信息列表
    List<ProductInfo> findList(List<String> productIds);

    //扣库存
    void decreaseStock(List<CarDTO> carDTOS);
}
