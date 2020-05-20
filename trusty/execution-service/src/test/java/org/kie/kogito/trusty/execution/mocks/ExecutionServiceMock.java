package org.kie.kogito.trusty.execution.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.test.Mock;
import org.kie.kogito.trusty.execution.IExecutionService;
import org.kie.kogito.trusty.execution.models.Execution;

@Mock
@ApplicationScoped
public class ExecutionServiceMock implements IExecutionService {

    private static final Map<String, Execution> storage = new HashMap<>();

    @Override
    public List<Execution> getExecutionHeaders(Date from, Date to, int limit, int offset, String prefix) {
        return new ArrayList<>();
    }

    @Override
    public boolean storeExecution(String executionId, Execution execution) {
        if (storage.containsKey(executionId)){
            return false;
        }
        storage.put(executionId, execution);
        return true;
    }
}
