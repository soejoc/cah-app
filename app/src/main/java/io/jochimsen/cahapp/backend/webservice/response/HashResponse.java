package io.jochimsen.cahapp.backend.webservice.response;

import lombok.Data;

@Data
public class HashResponse<T> {
    private T data;
    private int hash;
}
