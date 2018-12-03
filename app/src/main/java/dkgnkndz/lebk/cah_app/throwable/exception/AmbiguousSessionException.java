package dkgnkndz.lebk.cah_app.throwable.exception;

import dkgnkndz.lebk.cah_app.network.session.ServerSession;

public class AmbiguousSessionException extends RuntimeException {
    final ServerSession serverSession;

    public AmbiguousSessionException(final ServerSession serverSession) {
        super("There is already another session that has not been finished yet");

        this.serverSession = serverSession;
    }
}
