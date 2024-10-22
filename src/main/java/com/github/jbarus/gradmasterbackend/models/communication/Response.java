package com.github.jbarus.gradmasterbackend.models.communication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T, V> {
    private T status;
    private V responseBody;

    public Response(T status) {
        this.status = status;
    }

    public Response(T status, V responseBody) {
        this.status = status;
        this.responseBody = responseBody;
    }
}
