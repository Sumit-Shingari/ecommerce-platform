package com.nagp.ecommerce_poc.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private Long skuId;

    private String productName;

    private String brand;

    private String size;

    private String color;

    private Double price;

    private Integer quantity;

    private String imageUrl;

}
