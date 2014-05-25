package com.jamierf.dropwizard.template;

public class MissingParameterException extends IllegalArgumentException {
    public MissingParameterException(final String name) {
        super("Missing template parameter: " + name);
    }
}
