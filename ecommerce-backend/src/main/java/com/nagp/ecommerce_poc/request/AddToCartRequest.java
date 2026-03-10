package com.nagp.ecommerce_poc.request;

import lombok.Data;

@Data
public class AddToCartRequest {

    private Long skuId;
    private Integer quantity;

}
