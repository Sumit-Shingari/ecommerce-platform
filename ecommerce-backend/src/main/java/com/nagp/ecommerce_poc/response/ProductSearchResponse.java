package com.nagp.ecommerce_poc.response;

import com.nagp.ecommerce_poc.entity.ProductSearchDocument;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ProductSearchResponse {

    private List<ProductSearchDocument> products;

    private Map<String, Long> brandFacets;
    private Map<String, Long> sizeFacets;
    private Map<String, Long> colorFacets;
}