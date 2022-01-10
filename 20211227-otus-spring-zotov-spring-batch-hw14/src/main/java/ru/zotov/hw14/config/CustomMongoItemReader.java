package ru.zotov.hw14.config;

import lombok.Setter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.lang.NonNull;

import java.util.Iterator;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * @author Created by ZotovES on 06.01.2022
 * Кастомный Reader из Mongo с агрегацией
 */
@Setter
public class CustomMongoItemReader<T> extends MongoItemReader<T> {
    private MongoTemplate template;
    private Class<T> outputType;
    private MatchOperation match;
    private AggregationOperation[] operations;
    private String collection;

    @NonNull
    @Override
    protected Iterator<T> doPageRead() {
        Pageable pageable = PageRequest.of(page, pageSize);
        Aggregation agg = newAggregation(match, skip((long) pageable.getPageNumber() * pageable.getPageSize()),
                limit(pageable.getPageSize()));

        for (AggregationOperation operation : operations) {
            agg.getPipeline().add(operation);
        }
        return template.aggregate(agg, collection, outputType).iterator();
    }

    public void setTemplate(MongoTemplate template) {
        super.setTemplate(template);
        this.template = template;
    }
}
