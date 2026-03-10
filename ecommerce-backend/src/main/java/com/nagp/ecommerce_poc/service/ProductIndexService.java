package com.nagp.ecommerce_poc.service;

import com.nagp.ecommerce_poc.entity.Product;
import com.nagp.ecommerce_poc.entity.ProductSearchDocument;
import com.nagp.ecommerce_poc.entity.ProductSku;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductIndexService {

    private final ElasticsearchOperations operations;

    public void indexProduct(Product product) {

        List<ProductSku> skus = product.getSkus().stream().toList();

        Set<String> sizes = new HashSet<>();
        Set<String> colors = new HashSet<>();

        double minPrice = Double.MAX_VALUE;
        double maxPrice = 0;

        String imageUrl = null;

        if (skus != null) {

            for (ProductSku sku : skus) {

                if (sku.getSize() != null) {
                    sizes.add(sku.getSize());
                }

                if (sku.getColor() != null) {
                    colors.add(sku.getColor());
                }

                if (sku.getPrice() != null) {
                    minPrice = Math.min(minPrice, sku.getPrice());
                    maxPrice = Math.max(maxPrice, sku.getPrice());
                }

                if (imageUrl == null && sku.getImageUrl() != null) {
                    imageUrl = sku.getImageUrl(); // first image as thumbnail
                }
            }
        }

        List<String> suggestions = new ArrayList<>();

        if (product.getName() != null) {
            suggestions.add(product.getName());

            suggestions.addAll(Arrays.asList(product.getName().split(" ")));
        }

        if (product.getBrand() != null) {
            suggestions.add(product.getBrand());
        }

        if (product.getCategory() != null) {
            suggestions.add(product.getCategory());
        }

        if (product.getGender() != null) {
            suggestions.add(product.getGender());
        }

        ProductSearchDocument doc =
                ProductSearchDocument.builder()
                        .id(product.getId().toString())
                        .name(product.getName())
                        .brand(product.getBrand())
                        .category(product.getCategory())
                        .gender(product.getGender())
                        .sizes(new ArrayList<>(sizes))
                        .colors(new ArrayList<>(colors))
                        .minPrice(minPrice == Double.MAX_VALUE ? 0 : minPrice)
                        .maxPrice(maxPrice)
                        .imageUrl(imageUrl)
                        .thumbnailUrl(product.getThumbnailUrl())
                        .suggest(suggestions)
                        .build();

        operations.save(doc);

        System.out.println("Indexed product -> " + doc.getId());
    }
}
