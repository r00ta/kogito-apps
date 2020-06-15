package org.kie.kogito.persistence.mongo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;


final class KogitoDocument<K, V> implements Map.Entry<K, V> {
    @JsonProperty("entryId")
    private final K key;

    @JsonProperty("document")
    private V value;

    public KogitoDocument(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
}