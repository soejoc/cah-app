package dkgnkndz.lebk.cah_app.repository;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import dkgnkndz.lebk.cah_app.backend.local.session_key.SessionKey;
import dkgnkndz.lebk.cah_app.backend.local.session_key.SessionKeyDao;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

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
        Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() {
                return sessionKeyDao.save(sessionKey);
            }})
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteSessionKey() {
        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() {
                return sessionKeyDao.delete();
            }})
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }
}
