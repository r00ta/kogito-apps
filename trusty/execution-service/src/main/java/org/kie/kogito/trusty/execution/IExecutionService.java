package org.kie.kogito.trusty.execution;

import java.util.Date;
import java.util.List;

import org.kie.kogito.trusty.execution.models.Execution;

public interface IExecutionService {

    List<Execution> getExecutionHeaders(Date from, Date to, int limit, int offset, String prefix);

    boolean storeExecution(String executionId, Execution execution);
}
