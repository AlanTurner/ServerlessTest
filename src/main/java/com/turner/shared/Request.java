package com.turner.shared;

import java.util.Map;

public class Request {

    public static final String PATH_PARAMETERS = "pathParameters";
    private final Map<String, String> pathParameters;

    public Request(final Map<String, Object> input) {
        pathParameters = (Map<String, String>) input.get(PATH_PARAMETERS);
    }

    public int getIntPathParameter(final String name) {
        return Integer.parseInt(pathParameters.get(name));
    }


    @Override
    public String toString() {
        return "Request{" +
                "pathParameters=" + pathParameters +
                '}';
    }
}
