package org.kie.kogito.trusty.execution.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecutionsResponses {
        @JsonProperty("total")
        private int total;

        @JsonProperty("limit")
        private int limit;

        @JsonProperty("offset")
        private int offset;

        @JsonProperty("headers")
        private List<ExecutionHeaderResponse> headers;

        public ExecutionsResponses() {
        }

        public ExecutionsResponses(int total, int returnedRecords, int offset, List<ExecutionHeaderResponse> headers) {
            this.total = total;
            this.limit = returnedRecords;
            this.offset = offset;
            this.headers = headers;
        }

        public int getTotal(){
            return total;
        }

        public int getLimit(){
            return limit;
        }

        public int getOffset(){
            return offset;
        }

        public List<ExecutionHeaderResponse> getHeaders(){
            return headers;
        }
    }
