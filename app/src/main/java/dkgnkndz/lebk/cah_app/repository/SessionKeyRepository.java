package dkgnkndz.lebk.cah_app.repository;

import android.arch.lifecycle.LiveData;

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

    public LiveData<SessionKey> getSessionKey() {
        return sessionKeyDao.getSessionKey();
    }
}
