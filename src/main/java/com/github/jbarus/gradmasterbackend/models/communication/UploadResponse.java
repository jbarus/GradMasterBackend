package com.github.jbarus.gradmasterbackend.models.communication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadResponse<T> {
    private UploadResult result;
    private T data;

    public UploadResponse(UploadResult result) {
        this.result = result;
    }

    public UploadResponse(UploadResult result, T data) {
        this.result = result;
        this.data = data;
    }
}
