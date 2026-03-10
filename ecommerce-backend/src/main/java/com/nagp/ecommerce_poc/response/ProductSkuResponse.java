package com.nagp.ecommerce_poc.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSkuResponse {

    private Long skuId;
    private String size;
    private String color;
    private Double price;
    private Integer stock;
    private String imageUrl;

}
