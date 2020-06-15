package org.kie.kogito.persistence.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KogitoDocument {
    @JsonProperty("entryId")
    public String entryId;
}
