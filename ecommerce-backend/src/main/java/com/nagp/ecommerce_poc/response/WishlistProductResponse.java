package com.nagp.ecommerce_poc.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishlistProductResponse {

    private Long id;
    private String name;
    private String brand;
    private String imageUrl;
    private Double price;

}
