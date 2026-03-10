package com.nagp.ecommerce_poc.request;

import lombok.Data;

@Data
public class UpdateQuantityRequest {

    private Long itemId;

    private Integer quantity;

}
