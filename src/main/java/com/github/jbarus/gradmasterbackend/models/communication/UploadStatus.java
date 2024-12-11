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
    UNINITIALIZED_CONTEXT(2004, "Accessing uninitialized parts of context"),
    NO_SUCH_CONTEXT(2005, "No such context"),
    INVALID_UPDATE_SEQUENCE(2006, "Wrong sequence of file upload"),
    UNAUTHORIZED_MODIFICATION(2007, "Unauthorized modification"),
    UNDEFINED(2008, "Undefined error");

    private final int statusCode;
    private final String statusMessage;

    UploadStatus(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
