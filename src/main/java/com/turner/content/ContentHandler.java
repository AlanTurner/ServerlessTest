package com.turner.content;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.turner.shared.ErrorResponse;
import com.turner.shared.Request;
import com.turner.shared.Response;
import com.turner.shared.model.Article;
import com.turner.shared.service.DatabaseService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.Map;
import java.util.NoSuchElementException;

public class ContentHandler implements RequestHandler<Map<String, Object>, Response> {

    private static final Logger LOG = Logger.getLogger(ContentHandler.class);

    @Resource
    private DatabaseService databaseService = new DatabaseService();

    public static final String ID = "id";

    public ContentHandler() {
    }

    @Override
    public Response handleRequest(final Map<String, Object> input, final Context context) {
        try {

            Request request = new Request(input);

            LOG.info(String.format("Input request [%s]: %s ", context.getAwsRequestId(), request));

            Article articleById = databaseService.getArticleById(request.getIntPathParameter(ID));

            LOG.info(String.format("Obtained article [%s] ", articleById));

            return Response.builder()
                    .enableCors()
                    .setStatusCode(Response.SC_OK)
                    .setObjectBody(articleById)
                    .build();

        } catch (NoSuchElementException e) {
            LOG.error(String.format("Invalid article requested [%s]", context.getAwsRequestId()));
            return error(Response.SC_NOT_FOUND, "The article you requested was not found.");

        } catch (Exception e) {
            LOG.error(String.format("Error [%s] ", context.getAwsRequestId()), e);
            return error(Response.SC_SERVER_ERROR, "The request could not be processed.");

        }

    }


    private Response error(final int status, final String message) {
        return Response.builder()
                .enableCors()
                .setStatusCode(status)
                .setObjectBody(new ErrorResponse(message))
                .build();
    }


}
