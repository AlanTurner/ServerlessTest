package com.turner.shared.service;

import com.turner.shared.model.Article;
import com.turner.shared.model.Headline;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DatabaseServiceTest {

    private DatabaseService databaseService;

    @Before
    public void setup(){
        databaseService = new DatabaseService();
    }

    @Test
    public void testGetArticleById() throws IOException {
        Article articleById = databaseService.getArticleById(1);
        assertEquals("The article ID is different",1, articleById.getId());
        assertEquals("The article TITLE is incorrect.","Lorem Ipsum has been the industry's standard dummy text ever since the 1500s", articleById.getTitle());
        // .. we can continue..
    }

    @Test
    public void getArticles() throws IOException {
        Headline[] headlines = databaseService.getArticles();
        assertEquals("The number of articles returned is incorrect.", 10, headlines.length);
    }

}