package org.kie.kogito.trusty.service;

import java.util.Collections;
import java.util.Map;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

public class InfinispanServerTestResource implements QuarkusTestResourceWithCleanupLifecycleManager {

    private static final String LOCALHOST = "127.0.0.1";
    private static final int PORT = 11232;
    private static final String SASL_DIGEST_MD5 = "DIGEST-MD5";
    private static final String SERVER_NAME_INFINISPAN = "infinispan";
    private static final String REALM_DEFAULT = "default";
    private static final String ADMIN = "admin";
    private static final String INFINISPAN_IMAGE = System.getProperty("container.image.infinispan");
    private static final Logger LOGGER = LoggerFactory.getLogger(InfinispanServerTestResource.class);
    private GenericContainer infinispan;
    private RemoteCacheManager cacheManager;

    @Override
    public Map<String, String> start() {
        if (INFINISPAN_IMAGE == null) {

            LOGGER.warn("InfinispanServerTestResource started without an infinispan image properly set.");
            return Collections.emptyMap();
//            RuntimeException e = new RuntimeException();
//            e.printStackTrace();
//            throw new RuntimeException("Please define a valid Infinispan image in system property container.image.infinispan");
        }
        LOGGER.info("Using Infinispan image: {}", INFINISPAN_IMAGE);
        infinispan = new FixedHostPortGenericContainer(INFINISPAN_IMAGE)
                .withFixedExposedPort(PORT, 11222)
                .withReuse(false)
                .withEnv("USER", ADMIN)
                .withEnv("PASS", ADMIN)
                .withLogConsumer(new Slf4jLogConsumer(LOGGER))
                .waitingFor(Wait.forLogMessage(".*ISPN080001.*", 1));
        infinispan.start();
        return Collections.emptyMap();
    }

    @Override
    public void stop() {
        if (cacheManager != null) {
            cacheManager.close();
        }
        infinispan.stop();
    }

    private RemoteCacheManager getCacheManager() {
        if (cacheManager == null) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder
                    .addServer()
                    .host(LOCALHOST)
                    .port(PORT)
                    .security()
                    .authentication()
                    .username(ADMIN)
                    .password(ADMIN)
                    .realm(REALM_DEFAULT)
                    .serverName(SERVER_NAME_INFINISPAN)
                    .saslMechanism(SASL_DIGEST_MD5)
                    .clientIntelligence(ClientIntelligence.BASIC);

            cacheManager = new RemoteCacheManager(builder.build());
        }
        return cacheManager;
    }

    private boolean shouldCleanCache(String cacheName) {
        return cacheName.equals("processinstances")
                || cacheName.endsWith("_domain")
                || cacheName.equals("jobs")
                || cacheName.equals("usertaskinstances");
    }

    @Override
    public void cleanup() {
        if (!infinispan.isRunning()) {
            return;
        }

        for (String cacheName : getCacheManager().getCacheNames()) {
            if (shouldCleanCache(cacheName)) {
                LOGGER.debug("Cleaning cache " + cacheName);
                getCacheManager().getCache(cacheName).clear();
            }
        }
    }
}