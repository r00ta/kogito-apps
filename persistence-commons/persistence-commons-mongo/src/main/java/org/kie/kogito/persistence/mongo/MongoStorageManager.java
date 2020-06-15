package org.kie.kogito.persistence.mongo;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.kie.kogito.persistence.api.Storage;
import org.kie.kogito.persistence.api.StorageService;
import org.kie.kogito.persistence.api.factory.StorageQualifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.kie.kogito.persistence.mongo.Constants.MONGO_STORAGE;

@ApplicationScoped
@StorageQualifier(MONGO_STORAGE)
public class MongoStorageManager implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoStorageManager.class);

    private MongoDatabase database;

    @Inject
    MongoClient defaultMongoClient;

    // TODO: INJECT PROPERTY DB NAME
    @PostConstruct
    void setup() {
        database = defaultMongoClient.getDatabase("myMongoDb");
    }

    @Override
    public Storage<String, String> getCache(String name) {
        return null;
    }

    @Override
    public <T> Storage<String, T> getCache(String name, Class<T> type) {
        return new MongoStorageImpl<>(getOrCreateCollection(name, type));
    }

    @Override
    public <T> Storage<String, T> getCacheWithDataFormat(String name, Class<T> type, String rootType) {
        return null;
    }

    private MongoCollection getOrCreateCollection(String collection, Class type) {
        return database.getCollection(collection, type);
    }
}
