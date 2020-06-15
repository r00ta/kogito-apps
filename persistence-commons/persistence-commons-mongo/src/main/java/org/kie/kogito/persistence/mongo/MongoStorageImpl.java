package org.kie.kogito.persistence.mongo;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.mongodb.client.MongoCollection;
import org.kie.kogito.persistence.api.Storage;
import org.kie.kogito.persistence.api.query.Query;

public class MongoStorageImpl<K, V> implements Storage<K, V> {

    private MongoCollection<V> collection;

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
        return null;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getRootType() {
        return null;
    }
}
