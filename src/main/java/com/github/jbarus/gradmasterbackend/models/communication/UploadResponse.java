package com.github.jbarus.gradmasterbackend.models.communication;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UploadResponse {
    SUCCESS(2000,"Upload successful"),
    INVALID_INPUT(1001, "Invalid input file format"),
    INVALID_CONTENT(1002, "File does not contain all necessary data"),
    PARSING_ERROR(1003, "Error parsing content of file"),
    UNINITIALIZED_CONTEXT(1004, "Accessing uninitialized parts of context"),
    UNDEFINED(1005, "Undefined error");


    private final int statusCode;
    private final String statusMessage;
    UploadResponse(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
