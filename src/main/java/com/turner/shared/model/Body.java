package com.turner.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Body {

    private String type;
    private String body;

    public Body() {
    }

    public String getType() {
        return type;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return String.format("Body{ type='%s', body='%s' }", type, body);
    }
}
