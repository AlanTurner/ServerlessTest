package com.turner.shared.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turner.shared.model.Article;
import com.turner.shared.model.Headline;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

//A mock for AWS dynamo db for example..
public class DatabaseService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File file;

    public DatabaseService() {
        file = new File(getClass().getResource("/data.json").getFile());
    }

    public Article getArticleById(final int id) throws IOException {
        Article[] articlesArray = objectMapper.readValue(file, Article[].class);
        return Arrays.asList(articlesArray).stream()
                .filter(article -> article.getId() == id).findFirst().get();
    }

    public Headline[] getArticles() throws IOException {
        return objectMapper.readValue(file, Headline[].class);
    }

}
