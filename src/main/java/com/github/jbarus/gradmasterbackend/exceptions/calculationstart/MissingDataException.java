package com.github.jbarus.gradmasterbackend.exceptions.calculationstart;

import com.github.jbarus.gradmasterbackend.models.communication.CalculationStartStatus;
import lombok.Getter;

@Getter
public class MissingDataException extends RuntimeException {
    private final CalculationStartStatus status;

    public MissingDataException(CalculationStartStatus status) {
        this.status = status;
    }
}
