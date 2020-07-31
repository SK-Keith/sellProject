package com.keith.client;

import com.keith.common.dto.CarDTO;
import com.keith.common.model.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/msg/getMsg")
    String productMsg();

    @PostMapping("/product/listForOrder")
    List<ProductInfo> findByProductId(List<String> productIds);

    @PostMapping("/product/decreaseStock")
    void decreaseStock(@RequestBody List<CarDTO> carDTOS);
}
