package com.courseapp.course_search.service;

import co.elastic.clients.json.JsonData;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;

import com.courseapp.course_search.document.CourseDocument;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseSearchService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;
    private static final String INDEX = "courses";

    public List<CourseDocument> searchCourses(String keyword, String category, Integer minAge, Integer maxAge) {
        try {
            List<Query> filters = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                filters.add(MatchQuery.of(m -> m
                        .field("title")
                        .query(keyword)
                )._toQuery());
            }

            if (category != null && !category.isEmpty()) {
                filters.add(TermQuery.of(t -> t
                        .field("category.keyword")
                        .value(category)
                )._toQuery());
            }

            if (minAge != null) {
                filters.add(Query.of(q -> q
                        .range(r -> r
                                .field("minAge")
                                .gte(JsonData.of(minAge))
                        )
                ));
            }

            if (maxAge != null) {
                filters.add(Query.of(q -> q
                        .range(r -> r
                                .field("maxAge")
                                .lte(JsonData.of(maxAge))
                        )
                ));
            }




            Query finalQuery = BoolQuery.of(b -> b
                    .must(filters)
            )._toQuery();

            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index(INDEX)
                    .query(finalQuery)
            );

            SearchResponse<CourseDocument> response = elasticsearchClient.search(searchRequest, CourseDocument.class);

            List<CourseDocument> result = new ArrayList<>();
            response.hits().hits().forEach(hit -> result.add(hit.source()));
            return result;

        } catch (IOException e) {
            throw new RuntimeException("Elasticsearch query failed", e);
        }
    }
}
