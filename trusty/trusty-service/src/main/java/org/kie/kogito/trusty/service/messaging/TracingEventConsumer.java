package org.kie.kogito.trusty.service.messaging;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.kie.kogito.trusty.service.ITrustyService;
import org.kie.kogito.trusty.service.messaging.dto.TracingEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TracingEventConsumer {

        private static final Logger LOGGER = LoggerFactory.getLogger(TracingEventConsumer.class);

        @Inject
        ITrustyService trustyService;

        @Incoming("kogito-tracing")
        public void onProcessInstanceEvent(TracingEventDTO event) {
            processEvent(event);
        }

        protected void processEvent(TracingEventDTO event) {
            LOGGER.debug("Processing a new event");
            try{
//                DMNEventModel dmnEventModel = ModelFactory.fromKafkaCloudEvent(event);
                trustyService.storeExecution(null, null);
//                explainabilityService.processExecution(dmnEventModel.data.result);
            }
            catch (Exception e){
                LOGGER.warn("error", e);
            }
        }
    }
