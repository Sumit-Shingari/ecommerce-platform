package com.nagp.ecommerce_poc.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.nagp.ecommerce_poc.entity.ProductSearchDocument;
import com.nagp.ecommerce_poc.request.ProductSearchRequest;
import com.nagp.ecommerce_poc.response.ProductSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.AggregationsContainer;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ElasticsearchOperations operations;

    private final ElasticsearchClient client;

    public ProductSearchResponse search(ProductSearchRequest request) {

        // -----------------------------
        // 1️⃣ MAIN PRODUCT QUERY
        // -----------------------------

        BoolQuery.Builder mainBool = new BoolQuery.Builder();

        if (request.getQuery() != null && !request.getQuery().isBlank()) {
            mainBool.must(
                    MultiMatchQuery.of(m -> m
                            .query(request.getQuery())
                            .fields("brand^3", "name^2", "category")
                            .type(TextQueryType.BestFields)
                            .operator(Operator.And)
                    )._toQuery()
            );
        }

        // Apply ALL filters for product results
        applyFilters(mainBool, request);

        Query finalQuery = Query.of(q -> q.bool(mainBool.build()));

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(finalQuery)
                .withPageable(PageRequest.of(
                        request.getPage(),
                        request.getSizePerPage()
                ))
                .build();

        SearchHits<ProductSearchDocument> hits =
                operations.search(searchQuery, ProductSearchDocument.class);

        List<ProductSearchDocument> products = new ArrayList<>();
        hits.forEach(hit -> products.add(hit.getContent()));

        // -----------------------------
        // 2️⃣ FACET AGGREGATIONS
        // -----------------------------

        Map<String, Long> brandFacets =
                getFacetCounts(request, "brand", "brandAgg");

        Map<String, Long> sizeFacets =
                getFacetCounts(request, "sizes", "sizeAgg");

        Map<String, Long> colorFacets =
                getFacetCounts(request, "colors", "colorAgg");

        return ProductSearchResponse.builder()
                .products(products)
                .brandFacets(brandFacets)
                .sizeFacets(sizeFacets)
                .colorFacets(colorFacets)
                .build();
    }

    private void applyFilters(BoolQuery.Builder bool, ProductSearchRequest request) {

        if (request.getBrands() != null && !request.getBrands().isEmpty()) {
            bool.filter(Query.of(q -> q.terms(t -> t
                    .field("brand")
                    .terms(v -> v.value(
                            request.getBrands().stream()
                                    .map(FieldValue::of)
                                    .toList()
                    ))
            )));
        }

        if (request.getSizes() != null && !request.getSizes().isEmpty()) {
            bool.filter(Query.of(q -> q.terms(t -> t
                    .field("sizes")
                    .terms(v -> v.value(
                            request.getSizes().stream()
                                    .map(FieldValue::of)
                                    .toList()
                    ))
            )));
        }

        if (request.getColors() != null && !request.getColors().isEmpty()) {
            bool.filter(Query.of(q -> q.terms(t -> t
                    .field("colors")
                    .terms(v -> v.value(
                            request.getColors().stream()
                                    .map(FieldValue::of)
                                    .toList()
                    ))
            )));
        }
    }

    private Map<String, Long> getFacetCounts(
            ProductSearchRequest request,
            String fieldToAggregate,
            String aggName
    ) {

        BoolQuery.Builder facetBool = new BoolQuery.Builder();

        // 🔥 APPLY TEXT SEARCH (THIS WAS MISSING)
        if (request.getQuery() != null && !request.getQuery().isBlank()) {
            facetBool.must(
                    MultiMatchQuery.of(m -> m
                            .query(request.getQuery())
                            .fields("brand^3", "name^2", "category")
                            .type(TextQueryType.BestFields)
                            .operator(Operator.And)
                    )._toQuery()
            );
        }

        // Apply filters except the one being aggregated

        if (!fieldToAggregate.equals("brand") &&
                request.getBrands() != null &&
                !request.getBrands().isEmpty()) {

            facetBool.filter(Query.of(q -> q.terms(t -> t
                    .field("brand")
                    .terms(v -> v.value(
                            request.getBrands().stream()
                                    .map(FieldValue::of)
                                    .toList()
                    ))
            )));
        }

        if (!fieldToAggregate.equals("sizes") &&
                request.getSizes() != null &&
                !request.getSizes().isEmpty()) {

            facetBool.filter(Query.of(q -> q.terms(t -> t
                    .field("sizes")
                    .terms(v -> v.value(
                            request.getSizes().stream()
                                    .map(FieldValue::of)
                                    .toList()
                    ))
            )));
        }

        if (!fieldToAggregate.equals("colors") &&
                request.getColors() != null &&
                !request.getColors().isEmpty()) {

            facetBool.filter(Query.of(q -> q.terms(t -> t
                    .field("colors")
                    .terms(v -> v.value(
                            request.getColors().stream()
                                    .map(FieldValue::of)
                                    .toList()
                    ))
            )));
        }

        NativeQuery facetQuery = NativeQuery.builder()
                .withQuery(Query.of(q -> q.bool(facetBool.build())))
                .withAggregation(aggName,
                        Aggregation.of(a -> a
                                .terms(t -> t
                                        .field(fieldToAggregate)
                                        .size(20)
                                )
                        ))
                .withPageable(PageRequest.of(0, 1))
                .build();

        SearchHits<ProductSearchDocument> hits =
                operations.search(facetQuery, ProductSearchDocument.class);

        return extractTerms(hits.getAggregations(), aggName);
    }

    public List<String> suggest(String keyword) {

        List<String> suggestions = new ArrayList<>();

        try {

            SearchResponse<ProductSearchDocument> response =
                    client.search(s -> s
                                    .index("products")
                                    .suggest(su -> su
                                            .suggesters("product-suggest", sg -> sg
                                                    .prefix(keyword)
                                                    .completion(c -> c
                                                            .field("suggest")
                                                            .skipDuplicates(true)
                                                            .size(10)
                                                    )
                                            )
                                    ),
                            ProductSearchDocument.class
                    );

            if (response.suggest() != null) {

                response.suggest().get("product-suggest")
                        .forEach(entry ->
                                entry.completion().options()
                                        .forEach(opt ->
                                                suggestions.add(opt.text())
                                        )
                        );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return suggestions;
    }

    private Map<String, Long> extractTerms(
            AggregationsContainer<?> aggregationsContainer,
            String aggName) {

        Map<String, Long> result = new LinkedHashMap<>();

        if (aggregationsContainer == null) {
            return result;
        }

        // 🔥 Cast container to ElasticsearchAggregations
        ElasticsearchAggregations aggregations =
                (ElasticsearchAggregations) aggregationsContainer;

        ElasticsearchAggregation aggregation =
                aggregations.get(aggName);

        if (aggregation == null) return result;

        StringTermsAggregate terms =
                aggregation.aggregation().getAggregate().sterms();

        terms.buckets().array().forEach(bucket ->
                result.put(bucket.key().stringValue(),
                        bucket.docCount()));

        return result;
    }

    private Map<String, Long> extractNestedTerms(
            AggregationsContainer<?> container,
            String globalName,
            String termsName
    ) {

        Map<String, Long> result = new HashMap<>();

        if (container == null) return result;

        // 👇 Cast to ElasticsearchAggregations
        var esAggs = (org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations)
                container.aggregations();

        var globalAgg = esAggs.get(globalName);
        if (globalAgg == null) return result;

        var global = globalAgg.aggregation().getAggregate().global();
        if (global == null) return result;

        var nested = global.aggregations().get(termsName);
        if (nested == null) return result;

        var terms = nested.sterms();
        if (terms == null) return result;

        terms.buckets().array().forEach(bucket ->
                result.put(bucket.key().stringValue(), bucket.docCount())
        );

        return result;
    }
}