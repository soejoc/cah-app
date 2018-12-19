package dkgnkndz.lebk.cah_app.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import dkgnkndz.lebk.cah_app.backend.local.session_key.SessionKey;
import dkgnkndz.lebk.cah_app.backend.local.session_key.SessionKeyDao;
import io.reactivex.Maybe;

@Singleton
public class SessionKeyRepository {
    private final SessionKeyDao sessionKeyDao;

    @Inject
    public SessionKeyRepository(final SessionKeyDao sessionKeyDao) {
        this.sessionKeyDao = sessionKeyDao;
    }

    public Maybe<SessionKey> getSessionKey() {
        return sessionKeyDao.getSessionKey();
    }

    public void saveSessionKey(final SessionKey sessionKey) {
        sessionKeyDao.save(sessionKey);
    }
}
