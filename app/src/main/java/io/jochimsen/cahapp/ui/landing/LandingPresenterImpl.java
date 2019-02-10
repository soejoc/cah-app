package io.jochimsen.cahapp.ui.landing;

import android.util.Log;

import javax.inject.Inject;

import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.R;
import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKey;
import io.jochimsen.cahapp.di.scope.LandingActivityScope;
import io.jochimsen.cahapp.backend.BlackCardRepository;
import io.jochimsen.cahapp.backend.SessionKeyRepository;
import io.jochimsen.cahapp.backend.WhiteCardRepository;
import io.jochimsen.cahframework.protocol.object.message.request.RestartGameRequest;
import io.jochimsen.cahframework.protocol.object.message.response.FinishedGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.WaitForGameResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__({@Inject}))
@LandingActivityScope
public class LandingPresenterImpl implements LandingPresenter {
    private static final String TAG = "LandingPresenterImpl";

    private final LandingView landingView;
    private final SessionKeyRepository sessionKeyRepository;
    private final WhiteCardRepository whiteCardRepository;
    private final BlackCardRepository blackCardRepository;
    private final Subject<WaitForGameResponse> waitForGameSubject;
    private final Subject<StartGameResponse> startGameSubject;
    private final Subject<FinishedGameResponse> finishGameSubject;
    private final MyApp myApp;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onStart() {
        final Disposable waitForGameDisposable = waitForGameSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onWaitForGame, LandingPresenterImpl::onError);

        addDisposable(waitForGameDisposable);

        final Disposable startGameDisposable = startGameSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onStartGame, LandingPresenterImpl::onError);

        addDisposable(startGameDisposable);

        final Disposable finishedGameDisposable = finishGameSubject
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
        sessionKey.setSessionKey(startGameResponse.getSessionId());
        sessionKeyRepository.saveSessionKey(sessionKey);

        landingView.startGameActivity();
    }

    private void onFinishedGame(final FinishedGameResponse finishedGameResponse) {
        landingView.showStartGameFragment();
        sessionKeyRepository.deleteSessionKey();
    }

    private void onFetchedSessionKey(final SessionKey sessionKey) {
        if (sessionKey.getSessionKey() != null) {
            final RestartGameRequest restartGameRequest = new RestartGameRequest(sessionKey.getSessionKey());

            myApp.createConnection(restartGameRequest);
        } else {
            synchronizeWhiteCards();
        }
    }

    private void synchronizeWhiteCards() {
        whiteCardRepository.synchronize(this::synchronizeBlackCards, AndroidSchedulers.mainThread());
    }

    private void synchronizeBlackCards() {
        blackCardRepository.synchronize(landingView::showStartGameFragment, AndroidSchedulers.mainThread());
    }
}
