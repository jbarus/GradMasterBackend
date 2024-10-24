package com.github.jbarus.gradmasterbackend.models.communication;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CalculationStartStatus {
    SUCCESS(1000,"Upload successful"),
    MISSING_OR_INCOMPLETE_UNIVERSITY_EMPLOYEE(2001, "Missing university employees"),
    MISSING_OR_INCOMPLETE_STUDENT(2002, "Missing or incomplete students"),
    MISSING_OR_INCOMPLETE_PROBLEM_PARAMETER(2003, "Missing or incomplete problem parameters"),
    NO_SUCH_CONTEXT(2004, "No such context"),
    UNDEFINED(2005, "Undefined error");

    private final int statusCode;
    private final String statusMessage;

    CalculationStartStatus(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
