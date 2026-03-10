package com.nagp.ecommerce_poc.repository;

import com.nagp.ecommerce_poc.entity.ProductSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductSearchRepository
        extends ElasticsearchRepository<ProductSearchDocument, String> {
}
