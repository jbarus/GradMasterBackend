package com.github.jbarus.gradmasterbackend.exceptions;

public class MalformedRequestException extends RuntimeException {
    public MalformedRequestException(String message) {
        super(message);
    }
}
