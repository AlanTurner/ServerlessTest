package com.turner.content;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turner.shared.Response;
import com.turner.shared.Request;
import com.turner.shared.model.Article;
import com.turner.shared.service.DatabaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContentHandlerTest {

    @Mock
    private DatabaseService databaseService;

    private Map<String, Object> input = new HashMap<>();
    private Map<String, String> pathParams = new HashMap<>();

    @Mock
    private Context context;

    @InjectMocks
    public ContentHandler contentHandler = new ContentHandler();

    public ContentHandlerTest() throws IOException {
    }

    @Before
    public void setup() {
        input.put(Request.PATH_PARAMETERS, pathParams);
    }

    @Test
    public void whenIdValidThenReturnArticle() throws IOException {

        pathParams.put(ContentHandler.ID, "1");

        when(databaseService.getArticleById(1)).thenReturn(new Article(1, null, null, null, null));

        Response response = contentHandler.handleRequest(input, context);

        verify(databaseService, times(1)).getArticleById(1);

        assertEquals("Status code not OK", Response.SC_OK, response.getStatusCode());
        assertEquals("Article ID does not match", 1, new ObjectMapper().readValue(response.getBody(), Article.class).getId());

    }

    @Test
    public void whenArticleIdDoesntExistReturn404() throws IOException {

        pathParams.put(ContentHandler.ID, "1");

        when(databaseService.getArticleById(1)).thenThrow(new NoSuchElementException());

        Response response = contentHandler.handleRequest(input, context);

        verify(databaseService, times(1)).getArticleById(Mockito.anyInt());

        assertEquals("Status code not NOT FOUND", Response.SC_NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void whenInvalidParameterReturn500() throws IOException {

        pathParams.put(ContentHandler.ID, "Invalid");

        Response response = contentHandler.handleRequest(input, context);

        verify(databaseService, times(0)).getArticleById(Mockito.anyInt());

        assertEquals("Status code not NOT FOUND", Response.SC_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void whenDatabaseFailureReturn500() throws IOException {

        when(databaseService.getArticleById(1)).thenThrow(new IOException());

        Response response = contentHandler.handleRequest(input, context);

        verify(databaseService, times(0)).getArticleById(Mockito.anyInt());

        assertEquals("Status code not SERVER ERROR", Response.SC_SERVER_ERROR, response.getStatusCode());

    }

}