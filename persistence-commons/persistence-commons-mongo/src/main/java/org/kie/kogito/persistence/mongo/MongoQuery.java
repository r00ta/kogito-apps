package org.kie.kogito.persistence.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bson.conversions.Bson;
import org.kie.kogito.persistence.api.query.AttributeFilter;
import org.kie.kogito.persistence.api.query.AttributeSort;
import org.kie.kogito.persistence.api.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.regex;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static org.kie.kogito.persistence.api.query.FilterCondition.AND;
import static org.kie.kogito.persistence.api.query.FilterCondition.OR;

public class MongoQuery<T> implements Query<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoQuery.class);

    private Integer limit;
    private Integer offset;
    private List<AttributeFilter> filters;
    private List<AttributeSort> sortBy;
    private String rootType;

    @Override
    public Query<T> limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public Query<T> offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public Query<T> filter(List<AttributeFilter> filters) {
        this.filters = filters;
        return this;
    }

    @Override
    public Query<T> sort(List<AttributeSort> sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    @Override
    public List<T> execute() {
        List<Bson> conditions = new ArrayList<>();
    }

    private Bson createCondition(AttributeFilter filter) {
        switch (filter.getCondition()) {
            case CONTAINS:
                return regex(filter.getAttribute(), getValueForQueryString(filter.getValue()));
            case CONTAINS_ALL:
                return and((Iterable<Bson>) ((List) filter.getValue()).stream().map(x -> regex(filter.getAttribute(), getValueForQueryString(x))).iterator());
            case CONTAINS_ANY:
                return or((Iterable<Bson>) ((List) filter.getValue()).stream().map(x -> regex(filter.getAttribute(), getValueForQueryString(x))).iterator());
            case LIKE:
                return regex(filter.getAttribute(), getValueForQueryString(filter.getValue()) + "*");
            case EQUAL:
                return eq(filter.getAttribute(), filter.getValue());
            case IN:
                return format("o.%s in (%s)", filter.getAttribute(), ((List) filter.getValue()).stream().map(getValueForQueryString()).collect(joining(", ")));
            case IS_NULL:
                return format("o.%s is null", filter.getAttribute());
            case NOT_NULL:
                return format("o.%s is not null", filter.getAttribute());
            case BETWEEN:
                List<Object> value = (List<Object>) filter.getValue();
                return format("o.%s between %s and %s", filter.getAttribute(), getValueForQueryString().apply(value.get(0)), getValueForQueryString().apply(value.get(1)));
            case GT:
                return gt(filter.getAttribute(), filter.getValue());
            case GTE:
                return gte(filter.getAttribute(), filter.getValue());
            case LT:
                return lt(filter.getAttribute(), filter.getValue());;
            case LTE:
                return lte(filter.getAttribute(), filter.getValue());;
            case OR:
                return getRecursiveString(filter, OR);
            case AND:
                return getRecursiveString(filter, AND);
            default:
                return null;
        }
    }

    private static String getValueForQueryString(Object value) {
        return value instanceof String ? "'" + value + "'" : value.toString();
    }
}