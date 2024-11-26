package com.github.jbarus.gradmasterbackend.exceptions;

public class UninitializedContextException extends RuntimeException {
    public UninitializedContextException(String message) {
        super(message);
    }
}
