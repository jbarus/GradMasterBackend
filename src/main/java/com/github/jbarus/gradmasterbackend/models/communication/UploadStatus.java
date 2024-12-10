package com.github.jbarus.gradmasterbackend.models.communication;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UploadStatus {
    SUCCESS(1000,"Upload successful"),
    INVALID_INPUT(2001, "Invalid input file format"),
    INVALID_CONTENT(2002, "File does not contain all necessary data"),
    PARSING_ERROR(2003, "Error parsing content of file"),
    MULTIPLE_DATES(2004, "Multiple dates found"),
    UNINITIALIZED_CONTEXT(2005, "Accessing uninitialized parts of context"),
    INVALIDUPDATESEQUENCE(2006, "Wrong sequence of file upload"),
    UNAUTHORIZEDMODIFICATION(2007, "Unauthorized modification"),
    UNDEFINED(2008, "Undefined error");

    private final int statusCode;
    private final String statusMessage;

    UploadStatus(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
