package com.nagp.ecommerce_poc.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponse {

    private Long itemId;

    private Long skuId;

    private Long productId;

    private String productName;

    private String brand;

    private String size;

    private String color;

    private String imageUrl;

    private Double price;

    private Integer quantity;

}
