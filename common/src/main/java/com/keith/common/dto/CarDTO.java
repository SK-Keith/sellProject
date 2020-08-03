package com.keith.common.dto;

import lombok.Data;

/**
*   @Description: 购物车实体
*
*   @Author: SK-Keith
*   @Date: 2020/8/2
*/
@Data
public class CarDTO {

    private String productId;

    private int quantity;

    public CarDTO() {
    }

    public CarDTO(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
