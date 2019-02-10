package io.jochimsen.cahapp.backend.webservice.request;

import lombok.Value;

@Value(staticConstructor = "create")
public class CheckHashRequest {
    private final int hash;
}
