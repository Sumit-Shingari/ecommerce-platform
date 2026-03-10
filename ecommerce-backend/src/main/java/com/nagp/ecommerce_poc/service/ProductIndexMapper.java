package com.nagp.ecommerce_poc.service;

import com.nagp.ecommerce_poc.entity.Product;
import com.nagp.ecommerce_poc.entity.ProductSku;
import com.nagp.ecommerce_poc.entity.ProductAttribute;
import com.nagp.ecommerce_poc.entity.ProductSearchDocument;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductIndexMapper {

    public ProductSearchDocument map(Product product) {

        ProductSearchDocument doc = new ProductSearchDocument();

        doc.setId(product.getId().toString());
        doc.setName(product.getName());
        doc.setBrand(product.getBrand());
        doc.setCategory(product.getCategory());
        doc.setGender(product.getGender());
        doc.setThumbnailUrl(product.getThumbnailUrl());

        // ---------- SKU FLATTENING ----------
        List<ProductSku> skus = product.getSkus().stream().toList();

        Set<String> sizes = new HashSet<>();
        Set<String> colors = new HashSet<>();

        double minPrice = Double.MAX_VALUE;
        double maxPrice = 0;

        String imageUrl = null;

        for (ProductSku sku : skus) {

            sizes.add(sku.getSize());
            colors.add(sku.getColor());

            if (sku.getPrice() < minPrice) minPrice = sku.getPrice();
            if (sku.getPrice() > maxPrice) maxPrice = sku.getPrice();

            if (imageUrl == null) {
                imageUrl = sku.getImageUrl(); // first image as primary
            }
        }

        doc.setSizes(new ArrayList<>(sizes));
        doc.setColors(new ArrayList<>(colors));
        doc.setMinPrice(minPrice == Double.MAX_VALUE ? 0 : minPrice);
        doc.setMaxPrice(maxPrice);
        doc.setImageUrl(imageUrl);

        // ---------- ATTRIBUTES FLATTENING ----------
        Map<String, Object> attributesMap = new HashMap<>();

        if (product.getAttributes() != null) {
            for (ProductAttribute attr : product.getAttributes()) {
                attributesMap.put(attr.getAttributeKey(), attr.getAttributeValue());
            }
        }

        doc.setAttributes(attributesMap);

        // ---------- SUGGEST FIELD BUILDING ----------
        List<String> suggestions = new ArrayList<>();

        if (product.getName() != null) {
            suggestions.add(product.getName());

            // split words for better typeahead
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

        doc.setSuggest(suggestions);

        return doc;
    }
}
