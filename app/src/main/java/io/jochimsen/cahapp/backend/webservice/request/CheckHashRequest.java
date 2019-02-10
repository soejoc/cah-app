package io.jochimsen.cahapp.backend.webservice.request;

public class CheckHashRequest {

    public static CheckHashRequest create(final int hash) {
        return new CheckHashRequest(hash);
    }

    public final int hash;

    private CheckHashRequest(final int hash) {
        this.hash = hash;
    }
}
