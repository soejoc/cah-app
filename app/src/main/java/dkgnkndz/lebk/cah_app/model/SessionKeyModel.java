package dkgnkndz.lebk.cah_app.model;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import dkgnkndz.lebk.cah_app.backend.local.session.SessionKey;
import dkgnkndz.lebk.cah_app.repository.SessionKeyRepository;

@Singleton
public class SessionKeyModel {
    private final SessionKeyRepository sessionKeyRepository;
    private SessionKey sessionKey;

    @Inject
    public SessionKeyModel(final SessionKeyRepository sessionKeyRepository) {
        this.sessionKeyRepository = sessionKeyRepository;

        this.sessionKey = sessionKeyRepository.getSessionKey();
    }

    public UUID getSessionKey() {
        return (sessionKey != null) ? sessionKey.getSessionKey() : null;
    }

    public void saveSessionKey(final UUID sessionKey) {
        if(this.sessionKey == null) {
            this.sessionKey = new SessionKey();
        }

        this.sessionKey.setSessionKey(sessionKey);
        sessionKeyRepository.saveSessionKey(this.sessionKey);
    }
}
