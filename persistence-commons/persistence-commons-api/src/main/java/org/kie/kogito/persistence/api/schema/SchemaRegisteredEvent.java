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

package org.kie.kogito.persistence.api.schema;

import java.util.Objects;

public class SchemaRegisteredEvent {

    org.kie.kogito.persistence.api.schema.SchemaDescriptor schemaDescriptor;

    SchemaType schemaType;

    public SchemaRegisteredEvent(org.kie.kogito.persistence.api.schema.SchemaDescriptor schemaDescriptor, SchemaType type) {
        this.schemaDescriptor = schemaDescriptor;
        this.schemaType = type;
    }

    public org.kie.kogito.persistence.api.schema.SchemaDescriptor getSchemaDescriptor() {
        return schemaDescriptor;
    }

    public SchemaType getSchemaType() {
        return schemaType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchemaRegisteredEvent that = (SchemaRegisteredEvent) o;
        return Objects.equals(schemaDescriptor, that.schemaDescriptor) &&
                Objects.equals(schemaType, that.schemaType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schemaDescriptor, schemaType);
    }
}