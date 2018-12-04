package dkgnkndz.lebk.cah_app.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import dkgnkndz.lebk.cah_app.backend.local.session.SessionKey;
import dkgnkndz.lebk.cah_app.repository.SessionKeyRepository;

public class SessionKeyViewModel extends ViewModel {
    private final LiveData<SessionKey> sessionKey;
    private final SessionKeyRepository sessionKeyRepository;

    @Inject
    public SessionKeyViewModel(final SessionKeyRepository sessionKeyRepository) {
        this.sessionKeyRepository = sessionKeyRepository;

        this.sessionKey = sessionKeyRepository.getSessionKey();
    }
}
