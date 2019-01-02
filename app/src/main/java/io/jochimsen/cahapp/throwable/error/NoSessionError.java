package io.jochimsen.cahapp.throwable.error;

public class NoSessionError extends Error {
    public NoSessionError() {
        super("No session was found");
    }
}
