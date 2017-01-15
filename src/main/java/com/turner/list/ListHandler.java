package com.turner.list;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.turner.shared.ErrorResponse;
import com.turner.shared.Response;
import com.turner.shared.model.Headline;
import com.turner.shared.service.DatabaseService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.Map;

public class ListHandler implements RequestHandler<Map<String, Object>, Response> {

    private static final Logger LOG = Logger.getLogger(ListHandler.class);

    @Resource
    private DatabaseService databaseService = new DatabaseService();

    public ListHandler() {
    }

    @Override
    public Response handleRequest(final Map<String, Object> input, final Context context) {
        try {

            Headline[] headlines = databaseService.getArticles();

            LOG.info(String.format("Obtained headlines [%s] ", headlines));

            return Response.builder()
                    .enableCors()
                    .setStatusCode(Response.SC_OK)
                    .setObjectBody(headlines)
                    .build();

        } catch (Exception e) {
            LOG.error(String.format("Error [%s] ", context.getAwsRequestId()), e);
            return serverError();

        }

    }

    private Response serverError() {
        return Response.builder()
                .enableCors()
                .setStatusCode(Response.SC_SERVER_ERROR)
                .setObjectBody(new ErrorResponse("The request could not be processed."))
                .build();
    }
}
