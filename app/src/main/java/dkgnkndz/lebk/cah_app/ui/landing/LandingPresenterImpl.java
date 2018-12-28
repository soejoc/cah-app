package dkgnkndz.lebk.cah_app.ui.landing;

import android.util.Log;

import javax.inject.Inject;

import dkgnkndz.lebk.cah_app.MyApp;
import dkgnkndz.lebk.cah_app.R;
import dkgnkndz.lebk.cah_app.backend.local.entity.session_key.SessionKey;
import dkgnkndz.lebk.cah_app.network.handler.MessageSubject;
import dkgnkndz.lebk.cah_app.repository.BlackCardRepository;
import dkgnkndz.lebk.cah_app.repository.SessionKeyRepository;
import dkgnkndz.lebk.cah_app.repository.WhiteCardRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import protocol.object.message.request.RestartGameRequest;
import protocol.object.message.response.FinishedGameResponse;
import protocol.object.message.response.StartGameResponse;
import protocol.object.message.response.WaitForGameResponse;

public class LandingPresenterImpl implements LandingPresenter {
    private final LandingView landingView;
    private final SessionKeyRepository sessionKeyRepository;
    private final WhiteCardRepository whiteCardRepository;
    private final BlackCardRepository blackCardRepository;
    private final MyApp myApp;
    private final CompositeDisposable compositeDisposable;

    private static final String TAG = "LandingPresenterImpl";

    @Inject
    public LandingPresenterImpl(final LandingView landingView, final SessionKeyRepository sessionKeyRepository, final WhiteCardRepository whiteCardRepository, final BlackCardRepository blackCardRepository, final MyApp myApp) {
        this.landingView = landingView;
        this.sessionKeyRepository = sessionKeyRepository;
        this.whiteCardRepository = whiteCardRepository;
        this.blackCardRepository = blackCardRepository;
        this.myApp = myApp;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart() {
        final Disposable waitForGameDisposable = MessageSubject.waitForGameResponseSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onWaitForGame, LandingPresenterImpl::onError);

        addDisposable(waitForGameDisposable);

        final Disposable startGameDisposable = MessageSubject.startGameResponseSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onStartGame, LandingPresenterImpl::onError);

        addDisposable(startGameDisposable);

        final Disposable finishedGameDisposable = MessageSubject.finishedGameResponseSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFinishedGame, LandingPresenterImpl::onError);

        addDisposable(finishedGameDisposable);

        final Disposable sessionKeyDisposable = sessionKeyRepository.getSessionKey()
                .subscribeOn(Schedulers.io())
                .defaultIfEmpty(new SessionKey())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFetchedSessionKey, LandingPresenterImpl::onError);

        addDisposable(sessionKeyDisposable);
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
    }

    private void addDisposable(final Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    private static void onError(final Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }

    private void onWaitForGame(final WaitForGameResponse waitForGameResponse) {
        landingView.showWaitFragment(R.string.wait_for_game_message);
    }

    private void onStartGame(final StartGameResponse startGameResponse) {
        final SessionKey sessionKey = new SessionKey();
        sessionKey.setSessionKey(startGameResponse.sessionId);
        sessionKeyRepository.saveSessionKey(sessionKey);
    }

    private void onFinishedGame(final FinishedGameResponse finishedGameResponse) {
        landingView.showStartGameFragment();
        sessionKeyRepository.deleteSessionKey();
    }

    private void onFetchedSessionKey(final SessionKey sessionKey) {
        if (sessionKey.getSessionKey() != null) {
            final RestartGameRequest restartGameRequest = new RestartGameRequest();
            restartGameRequest.sessionKey = sessionKey.getSessionKey();

            myApp.createConnection(restartGameRequest);
        } else {
            synchronizeWhiteCards();
            synchronizeBlackCards();
        }
    }

    private void synchronizeWhiteCards() {
        whiteCardRepository.synchronize(this::synchronizeWhiteCards, AndroidSchedulers.mainThread());
    }

    private void synchronizeBlackCards() {
        //ToDo: Schwarze Karten analog zu den weißen Karten synchronisieren. Das Schema für die weißen Katen kann vollständig übernommen werden. Nach dem Synchronisieren der schwarzen Karten soll das StartGameFragment per landingView.showStartGameFragment() angezeigt werden
        blackCardRepository.synchronize(this::synchronizeBlackCards, AndroidSchedulers.mainThread());
        landingView.showStartGameFragment();
    }
}
