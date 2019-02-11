package io.jochimsen.cahapp.backend;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKey;
import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKeyDao;
import io.jochimsen.cahapp.di.scope.AppScope;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;

@AppScope
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class SessionKeyRepository {
    private final SessionKeyDao sessionKeyDao;

    public Maybe<SessionKey> getSessionKey() {
        return sessionKeyDao.getSessionKey();
    }

    public void saveSessionKey(final SessionKey sessionKey) {
        Completable.fromAction(() -> sessionKeyDao.save(sessionKey))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteSessionKey() {
        Completable.fromAction(sessionKeyDao::delete)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }
}
