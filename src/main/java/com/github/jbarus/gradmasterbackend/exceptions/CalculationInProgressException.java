package com.github.jbarus.gradmasterbackend.exceptions;

public class CalculationInProgressException extends RuntimeException {
    public CalculationInProgressException(String message) {
        super(message);
    }
}
