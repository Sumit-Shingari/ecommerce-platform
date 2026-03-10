package com.nagp.ecommerce_poc.service;

import com.nagp.ecommerce_poc.entity.Product;
import com.nagp.ecommerce_poc.repository.ProductRepository;
import com.nagp.ecommerce_poc.response.ProductDetailResponse;
import com.nagp.ecommerce_poc.response.ProductSkuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductIndexService indexService;

    public Product save(Product product) {

        Product saved = productRepository.save(product);

        indexService.indexProduct(saved);

        return saved;
    }

    public ProductDetailResponse getProduct(Long id) {

            Product product = productRepository
                    .findWithDetailsById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            List<ProductSkuResponse> skus = product.getSkus()
                    .stream()
                    .map(sku -> ProductSkuResponse.builder()
                            .skuId(sku.getId())
                            .size(sku.getSize())
                            .color(sku.getColor())
                            .price(sku.getPrice())
                            .stock(sku.getStock())
                            .imageUrl(sku.getImageUrl())
                            .build())
                    .toList();

            Map<String,String> attributes = new HashMap<>();

            if(product.getAttributes()!=null){

                product.getAttributes()
                        .forEach(a ->
                                attributes.put(
                                        a.getAttributeKey(),
                                        a.getAttributeValue()
                                )
                        );
            }

            return ProductDetailResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .brand(product.getBrand())
                    .category(product.getCategory())
                    .gender(product.getGender())
                    .description(product.getDescription())
                    .skus(skus)
                    .attributes(attributes)
                    .build();
        }
}