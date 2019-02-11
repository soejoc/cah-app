package io.jochimsen.cahapp.backend.webservice.response;

import lombok.Data;

@Data
public class CheckHashResponse {
    private boolean hashEqual;
}
