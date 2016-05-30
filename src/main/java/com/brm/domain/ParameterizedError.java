package com.brm.domain;

import java.io.Serializable;

/**
 * Value object for sending a parameterized error message.
 */
public class ParameterizedError implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String message;
    private final String[] params;

    public ParameterizedError(String message, String... params) {
        this.message = message;
        this.params = params;
    }

    public String getMessage() {
        return message;
    }

    public String[] getParams() {
        return params;
    }

}
