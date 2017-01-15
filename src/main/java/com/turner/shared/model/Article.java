package com.turner.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Article extends Headline{

    private Body[] body;
    private String cover;
    private String url;

    public Article() {
    }

    public Article(final int id, final String title, final Body[] body, final String cover, final String url) {
        super(id, title);
        this.body = body;
        this.cover = cover;
        this.url = url;
    }

    public Body[] getBody() {
        return body;
    }

    public String getCover() {
        return cover;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return String.format("Article{ id='%s', headlines='%s' , body='%s' , cover='%s' , url='%s' }",
                getId(), getTitle(), body, cover, url);
    }
}
