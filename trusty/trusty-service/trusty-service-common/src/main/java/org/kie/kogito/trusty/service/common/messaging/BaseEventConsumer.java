/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.kogito.trusty.service.common.messaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.kie.kogito.cloudevents.CloudEventUtils;
import org.kie.kogito.trusty.service.common.TrustyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.CloudEvent;
import io.smallrye.reactive.messaging.kafka.IncomingKafkaRecordMetadata;
import io.smallrye.reactive.messaging.kafka.KafkaMessageMetadata;
import io.smallrye.reactive.messaging.kafka.OutgoingKafkaRecordMetadata;

public abstract class BaseEventConsumer<E> {

    private static final Logger LOG = LoggerFactory.getLogger(BaseEventConsumer.class);

    protected final TrustyService service;
    private final ObjectMapper mapper;

    protected BaseEventConsumer() {
        this(null, null);
    }

    public BaseEventConsumer(final TrustyService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    protected CompletionStage<Void> handleMessage(final Message<String> message) {
        try {
            CloudEventUtils.decode(message.getPayload()).ifPresent(this::handleCloudEvent);
        } catch (Exception e) {
            LOG.error("Something unexpected happened during the processing of an Event. The event is discarded.", e);
            Optional<IncomingKafkaRecordMetadata> metadata = message.getMetadata(IncomingKafkaRecordMetadata.class);//.orElse(buildFailedCounterMetadata(0));
            int retryCounter = 1;
            if (metadata.isPresent()) {
                LOG.info("metadata present");
                Header header = metadata.get().getHeaders().lastHeader("my-header");
                if (header != null) {
                    LOG.info("header present");
                    retryCounter = intFromByteArray(header.value());
                }
            } else {
                LOG.info("metadata not there");
            }
            System.out.println("SUCAAAAAAAAAAAAAA " + retryCounter);
            if (retryCounter >= 10) {
                LOG.error("retry counter = 10");
                return message.ack();
            }

            List<RecordHeader> headers = new ArrayList<>();
            headers.add(new RecordHeader("my-header", intToByteArray(retryCounter + 1)));
            headers.add(new RecordHeader("nextTimestamp", intToByteArray((int) System.currentTimeMillis() / 1000 + 60)));

            OutgoingKafkaRecordMetadata<String> newMetadata = OutgoingKafkaRecordMetadata.<String> builder()
                    .withKey("my-key")
                    .withHeaders(headers)
                    .build();

            sendEventToRetryTopic(message.getPayload(), newMetadata);
        }
        return message.ack();
    }

    protected abstract void sendEventToRetryTopic(String payload, KafkaMessageMetadata metadata);

    protected CompletionStage<Void> handleFailedMessage(final Message<String> message) {
        int nextTimestamp = getRetryTimeout(message);
        int currentTime = (int) System.currentTimeMillis() / 1000;
        if (nextTimestamp - currentTime > 0) {
            try {
                LOG.info("sleeping for " + (nextTimestamp - currentTime) * 1000);
                Thread.sleep((nextTimestamp - currentTime) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOG.debug("processing failed message");
        return handleMessage(message);
    }

    protected int getRetryTimeout(final Message<String> message) {
        Optional<IncomingKafkaRecordMetadata> metadata = message.getMetadata(IncomingKafkaRecordMetadata.class);
        if (metadata.isPresent()) {
            LOG.info("metadata present");
            Header header = metadata.get().getHeaders().lastHeader("nextTimestamp");
            if (header != null) {
                LOG.info("header present");
                return intFromByteArray(header.value());
            }
        } else {
            LOG.info("metadata not there");
        }
        return 0;
    }

    private int intFromByteArray(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF) << 0);
    }

    public static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value };
    }

    protected void handleCloudEvent(final CloudEvent cloudEvent) {
        final E payload;
        try {
            payload = mapper.readValue(cloudEvent.getData(), getEventType());
        } catch (IOException e) {
            LOG.error("Unable to deserialize CloudEvent data as " + getEventType().getType().getTypeName(), e);
            return;
        }
        if (payload == null) {
            LOG.error("Received CloudEvent with id {} from {} with empty data", cloudEvent.getId(), cloudEvent.getSource());
            return;
        }
        LOG.debug("Received CloudEvent with id {} from {}", cloudEvent.getId(), cloudEvent.getSource());
        internalHandleCloudEvent(cloudEvent, payload);
    }

    protected abstract TypeReference<E> getEventType();

    protected abstract void internalHandleCloudEvent(final CloudEvent cloudEvent, final E payload);
}
