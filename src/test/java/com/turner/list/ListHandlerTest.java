package com.turner.list;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turner.content.ContentHandler;
import com.turner.shared.Response;
import com.turner.shared.model.Headline;
import com.turner.shared.service.DatabaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListHandlerTest {

    @Mock
    private DatabaseService databaseService;

    private Map<String, Object> input = new HashMap<>();

    @Mock
    private Context context;

    @InjectMocks
    public ListHandler listHandler = new ListHandler();

    @Test
    public void WhenGoodRequestReturnHeadlines() throws IOException {

        Headline[] headliners = {
                new Headline(1, "title1"),
                new Headline(2, "title2")
        };

        when(databaseService.getArticles()).thenReturn(headliners);

        Response response = listHandler.handleRequest(input, context);

        verify(databaseService, times(1)).getArticles();

        assertEquals("Status code not OK", Response.SC_OK, response.getStatusCode());
        assertEquals("Article ID does not match", 1, new ObjectMapper().readValue(response.getBody(), Headline[].class)[0].getId());

    }

    @Test
    public void whenDatabaseFailureReturn500() throws IOException {

        when(databaseService.getArticles()).thenThrow(new IOException());

        Response response = listHandler.handleRequest(input, context);

        verify(databaseService, times(1)).getArticles();

        assertEquals("Status code not SERVER ERROR", Response.SC_SERVER_ERROR, response.getStatusCode());

    }


}