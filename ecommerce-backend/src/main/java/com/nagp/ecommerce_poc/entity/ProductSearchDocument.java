package com.nagp.ecommerce_poc.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;

import java.util.List;
import java.util.Map;

@Document(indexName = "products", writeTypeHint = WriteTypeHint.FALSE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDocument {

    @Id
    private String id;

    private String name;
    private String brand;
    private String category;
    private String gender;

    private List<String> sizes;
    private List<String> colors;

    private Double minPrice;
    private Double maxPrice;

    private String imageUrl;
    private String thumbnailUrl;

    private Map<String,Object> attributes;

    private List<String> suggest;
}
