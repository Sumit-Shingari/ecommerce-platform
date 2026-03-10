package com.nagp.ecommerce_poc.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductSearchRequest {

    private String query;
    private List<String> brands;
    private List<String> sizes;
    private List<String> colors;

    private Double priceFrom;
    private Double priceTo;
    private String sort; // price_asc | price_desc

    private int page = 0;
    private int sizePerPage = 20;
}
