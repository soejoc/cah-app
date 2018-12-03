package dkgnkndz.lebk.cah_app.throwable.error;

public class NoSessionError extends Error {
    public NoSessionError() {
        super("No session was found");
    }
}
