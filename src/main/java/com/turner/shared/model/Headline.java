package com.turner.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Headline {

    private int id;
    private String title;

    public Headline() {
    }

    public Headline(final int id, final String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.format("Headline{ id='%s', title='%s' }", id, title);
    }
}
