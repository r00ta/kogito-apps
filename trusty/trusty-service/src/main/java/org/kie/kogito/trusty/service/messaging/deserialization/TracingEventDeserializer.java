package org.kie.kogito.trusty.service.messaging.deserialization;

import org.kie.kogito.trusty.service.messaging.dto.TracingEventDTO;

public class TracingEventDeserializer extends AbstractCloudEventDeserializer<TracingEventDTO> {

    public TracingEventDeserializer() {
        super(TracingEventDTO.class);
    }
}