package org.kie.kogito.trusty.execution;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.resteasy.spi.NotImplementedYetException;
import org.kie.kogito.trusty.execution.models.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ExecutionService implements IExecutionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionService.class);

    @Override
    public List<Execution> getExecutionHeaders(Date from, Date to, int limit, int offset, String prefix){
        throw new NotImplementedYetException("Not implemented yet.");
    }

    @Override
    public boolean storeExecution(String executionId, Execution execution) {
        throw new NotImplementedYetException("Not implemented yet.");
    }
}
