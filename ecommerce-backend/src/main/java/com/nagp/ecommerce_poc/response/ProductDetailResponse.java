package com.nagp.ecommerce_poc.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ProductDetailResponse {

    private Long id;
    private String name;
    private String brand;
    private String category;
    private String gender;
    private String description;

    private List<ProductSkuResponse> skus;

    private Map<String,String> attributes;

}
