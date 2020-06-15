package org.kie.kogito.persistence.mongo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.mongodb.client.MongoCollection;
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
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.not;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.regex;

public class MongoQuery<T> implements Query<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoQuery.class);

    private MongoCollection<T> collection;
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
        List<Bson> conditions = filters.stream().map(x -> createCondition(x)).collect(Collectors.toList());
        Iterator<T> it = collection.find(and(conditions)).iterator();

        List<T> copy = new ArrayList<T>();
        while (it.hasNext()) {
            copy.add(it.next());
        }
        return copy;
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
                return in(filter.getAttribute(), ((List) filter.getValue()).stream().iterator());
            case IS_NULL:
                return eq(filter.getAttribute(), null);
            case NOT_NULL:
                return not(eq(filter.getAttribute(), null));
            case BETWEEN:
                List<Object> value = (List<Object>) filter.getValue();
                return and(gte(filter.getAttribute(), value.get(0)), lte(filter.getAttribute(), value.get(1)));
            case GT:
                return gt(filter.getAttribute(), filter.getValue());
            case GTE:
                return gte(filter.getAttribute(), filter.getValue());
            case LT:
                return lt(filter.getAttribute(), filter.getValue());
            case LTE:
                return lte(filter.getAttribute(), filter.getValue());
            case OR:
                return or(createCondition(filter));
            case AND:
                return and(createCondition(filter));
            default:
                return null;
        }
    }

    private static String getValueForQueryString(Object value) {
        return value instanceof String ? "'" + value + "'" : value.toString();
    }
}