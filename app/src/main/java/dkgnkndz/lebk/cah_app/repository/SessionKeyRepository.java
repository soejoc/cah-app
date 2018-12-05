package dkgnkndz.lebk.cah_app.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import dkgnkndz.lebk.cah_app.backend.local.session.SessionKey;
import dkgnkndz.lebk.cah_app.backend.local.session.SessionKeyDao;

@Singleton
public class SessionKeyRepository {
    private final SessionKeyDao sessionKeyDao;

    @Inject
    public SessionKeyRepository(final SessionKeyDao sessionKeyDao) {
        this.sessionKeyDao = sessionKeyDao;
    }

    public SessionKey getSessionKey() {
        return sessionKeyDao.getSessionKey();
    }

    public void saveSessionKey(final SessionKey sessionKey) {
        sessionKeyDao.save(sessionKey);
    }
}
