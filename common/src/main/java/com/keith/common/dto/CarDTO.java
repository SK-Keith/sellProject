package com.keith.common.dto;

import lombok.Data;

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
