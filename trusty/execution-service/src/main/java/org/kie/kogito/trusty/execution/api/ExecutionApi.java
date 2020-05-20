package org.kie.kogito.trusty.execution.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.kie.kogito.trusty.execution.ExecutionService;
import org.kie.kogito.trusty.execution.IExecutionService;
import org.kie.kogito.trusty.execution.models.Execution;
import org.kie.kogito.trusty.execution.responses.ExecutionHeaderResponse;
import org.kie.kogito.trusty.execution.responses.ExecutionsResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/executions")
public class ExecutionApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionApi.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Inject
    IExecutionService executionService;

    @GET
    @APIResponses(value = {
            @APIResponse(description = "Returns the execution headers.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = ExecutionsResponses.class))),
            @APIResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    }
    )
    @Operation(summary = "Gets the execution headers", description = "Gets the execution headers.")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExecutions(
            @Parameter(
                    name = "from",
                    description = "Start datetime for the lookup. Date in the format \"yyyy-MM-dd'T'HH:mm:ssZ\"",
                    required = true,
                    schema = @Schema(implementation = String.class)
            ) @QueryParam("from") String from,
            @Parameter(
                    name = "to",
                    description = "End datetime for the lookup. Date in the format \"yyyy-MM-dd'T'HH:mm:ssZ\"",
                    required = true,
                    schema = @Schema(implementation = String.class)
            ) @QueryParam("to") String to,
            @Parameter(
                    name = "limit",
                    description = "Maximum number of results to return.",
                    required = false,
                    schema = @Schema(implementation = Integer.class)
            ) @DefaultValue("100") @QueryParam("limit") int limit,
            @Parameter(
                    name = "offset",
                    description = "Offset for the pagination.",
                    required = false,
                    schema = @Schema(implementation = Integer.class)
            ) @DefaultValue("0") @QueryParam("offset") int offset,
            @Parameter(
                    name = "search",
                    description = "Execution ID prefix to be matched",
                    required = false,
                    schema = @Schema(implementation = String.class)
            ) @DefaultValue("") @QueryParam("search") String prefix) throws ParseException {

        if (from == null || to == null || limit < 0 || offset < 0){
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "Invalid parameters value").build();
        }

        Date fromDate;
        Date toDate;
        try {
            fromDate = DATE_FORMAT.parse(from);
            toDate = DATE_FORMAT.parse(to);
        } catch (ParseException e) {
            LOGGER.warn("Invalid date", e);
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "Date format should be yyyy-MM-dd'T'HH:mm:ssZ").build();
        }

        List<Execution> executions = executionService.getExecutionHeaders(fromDate, toDate, limit, offset, prefix);

        List<ExecutionHeaderResponse> headersResponses = new ArrayList<>();
        executions.forEach(x -> headersResponses.add(ExecutionHeaderResponse.fromExecution(x)));
        return Response.ok(new ExecutionsResponses(headersResponses.size(), limit, offset, headersResponses)).build();
    }
}
