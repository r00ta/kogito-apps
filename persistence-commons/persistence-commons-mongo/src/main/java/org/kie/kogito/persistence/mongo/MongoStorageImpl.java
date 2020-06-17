package org.kie.kogito.persistence.mongo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.mongodb.client.MongoCollection;
import org.kie.kogito.persistence.api.Storage;
import org.kie.kogito.persistence.api.query.Query;

import static com.mongodb.client.model.Filters.eq;

public class MongoStorageImpl<K, V> implements Storage<K, V> {

    private MongoCollection collection;
    private String rootType;

    public MongoStorageImpl(MongoCollection collection, String rootType) {
        this.collection = collection;
        this.rootType = rootType;
    }

    @Override
    public void addObjectCreatedListener(Consumer<V> consumer) {

    }

    @Override
    public void addObjectUpdatedListener(Consumer<V> consumer) {

    }

    @Override
    public void addObjectRemovedListener(Consumer<K> consumer) {

    }

    @Override
    public Query<V> query() {
        return new MongoQuery<>(collection, rootType);
    }

    @Override
    public V get(Object key) {
        KogitoDocument<K, V> document = (KogitoDocument<K, V>) collection.find(eq("entryId", key)).iterator().next();
        return document.getValue();
    }

    @Override
    public V put(K key, V value) {
        collection.insertOne(new KogitoDocument(key, value));
        return value;
    }

    @Override
    public V remove(Object key) {
        KogitoDocument<K, V> document = (KogitoDocument<K, V>) collection.findOneAndDelete(eq("entryId", key));
        return document.getValue();
    }

    @Override
    public boolean containsKey(K key) {
        return collection.countDocuments(eq("entryId", key)) > 0;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Iterator<KogitoDocument> it = collection.find().iterator();

        List<KogitoDocument> copy = new ArrayList<KogitoDocument>();
        while (it.hasNext()) {
            copy.add(it.next());
        }

        return new HashSet<>(copy);
    }

    @Override
    public void clear() {
        collection.drop();
    }

    @Override
    public String getRootType() {
        return rootType;
    }
}
