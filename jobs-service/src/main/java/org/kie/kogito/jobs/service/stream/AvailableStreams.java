/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.kogito.jobs.service.stream;

public final class AvailableStreams {

    public static final String JOB_ERROR = "job-error";
    public static final String JOB_ERROR_EVENTS = "job-error-events";
    public static final String JOB_SUCCESS = "job-success";
    public static final String JOB_SUCCESS_EVENTS = "job-success-events";
    public static final String JOB_STATUS_CHANGE = "job-status-change";
    public static final String JOB_STATUS_CHANGE_EVENTS = "job-status-change-events";
    public static final String JOB_STATUS_CHANGE_EVENTS_TOPIC = "kogito-job-service-job-status-events";

    private AvailableStreams() {

    }
}
