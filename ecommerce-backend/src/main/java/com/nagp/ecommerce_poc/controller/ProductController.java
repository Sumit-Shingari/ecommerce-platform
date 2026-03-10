package com.nagp.ecommerce_poc.controller;

import com.nagp.ecommerce_poc.entity.ProductSearchDocument;
import com.nagp.ecommerce_poc.request.ProductSearchRequest;
import com.nagp.ecommerce_poc.response.ProductDetailResponse;
import com.nagp.ecommerce_poc.response.ProductSearchResponse;
import com.nagp.ecommerce_poc.service.ProductSearchService;
import com.nagp.ecommerce_poc.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductSearchService searchService;

    private final ProductService productService;

    @PostMapping("/search")
    public ProductSearchResponse search(
            @RequestBody ProductSearchRequest request) {
        return searchService.search(request);
    }

    @GetMapping("/suggest")
    public List<String> suggest(@RequestParam String q) {
        return searchService.suggest(q);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProduct(
            @PathVariable Long id){

        return ResponseEntity.ok(
                productService.getProduct(id)
        );
    }
}
