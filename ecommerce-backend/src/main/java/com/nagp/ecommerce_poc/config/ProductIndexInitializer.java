package com.nagp.ecommerce_poc.config;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.nagp.ecommerce_poc.repository.ProductRepository;
import com.nagp.ecommerce_poc.service.ProductIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class ProductIndexInitializer {

    private final ElasticsearchClient client;
    private final ProductRepository productRepository;
    private final ProductIndexService indexService;

    @Bean
    public ApplicationRunner initIndex() {

        return args -> {

            String indexName = "products";

            try {

                boolean exists;

                try {
                    client.indices().get(g -> g.index(indexName));
                    exists = true;
                } catch (Exception ex) {
                    exists = false;
                }

                // ---------- DELETE OLD INDEX ----------
                if (exists) {
                    System.out.println("Deleting existing products index...");
                    client.indices().delete(d -> d.index(indexName));
                }

                // ---------- CREATE INDEX FROM JSON ----------
                System.out.println("Creating products index from JSON...");

                ClassPathResource resource =
                        new ClassPathResource("elasticsearch/product-index.json");

                String mapping =
                        new String(resource.getInputStream().readAllBytes(),
                                StandardCharsets.UTF_8);

                client.indices().create(c -> c
                        .index(indexName)
                        .withJson(new java.io.StringReader(mapping))
                );

                System.out.println("Products index created successfully");

                // ---------- AUTO INDEX DB DATA ----------
                System.out.println("Reindexing products from database...");

                productRepository.findAll()
                        .forEach(indexService::indexProduct);

                System.out.println("Reindex completed");

            } catch (Exception e) {
                System.out.println("Index initialization failed");
                e.printStackTrace();
            }
        };
    }
}