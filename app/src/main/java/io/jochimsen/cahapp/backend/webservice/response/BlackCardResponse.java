package io.jochimsen.cahapp.backend.webservice.response;

import lombok.Data;

@Data
public class BlackCardResponse {
    private long blackCardId;
    private String text;
    private int blankCount;
}
