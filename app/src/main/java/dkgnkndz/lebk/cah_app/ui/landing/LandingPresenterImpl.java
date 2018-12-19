package dkgnkndz.lebk.cah_app.ui.landing;

import android.util.Log;

import javax.inject.Inject;

import dkgnkndz.lebk.cah_app.MyApp;
import dkgnkndz.lebk.cah_app.R;
import dkgnkndz.lebk.cah_app.backend.local.session_key.SessionKey;
import dkgnkndz.lebk.cah_app.network.handler.MessageSubject;
import dkgnkndz.lebk.cah_app.repository.SessionKeyRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import protocol.object.message.request.RestartGameRequest;
import protocol.object.message.response.FinishedGameResponse;
import protocol.object.message.response.StartGameResponse;
import protocol.object.message.response.WaitForGameResponse;

public class LandingPresenterImpl implements LandingPresenter {
    private final LandingView landingView;
    private final SessionKeyRepository sessionKeyRepository;
    private final CompositeDisposable compositeDisposable;
    private final MyApp myApp;

    private static final String TAG = "LandingPresenterImpl";

    @Inject
    public LandingPresenterImpl(final LandingView landingView, final SessionKeyRepository sessionKeyRepository, final MyApp myApp) {
        this.landingView = landingView;
        this.sessionKeyRepository = sessionKeyRepository;
        this.compositeDisposable = new CompositeDisposable();
        this.myApp = myApp;
    }

    @Override
    public void onStart() {
        subscribeToWaitForGame();
        subscribeToStartGame();
        subscribeToFinishedGame();
        subscribeToLoadSessionKey();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
    }

    private void addDisposable(final Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    private void subscribeToWaitForGame() {
        final Disposable disposable = MessageSubject.waitForGameResponseSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<WaitForGameResponse>() {
                            @Override
                            public void accept(final WaitForGameResponse waitForGameResponse) throws Exception {
                                landingView.showWaitFragment(R.string.wait_for_game_message);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(final Throwable throwable) throws Exception {
                                Log.d(TAG, throwable.getMessage());
                            }
                        }
                );

        addDisposable(disposable);
    }

    private void subscribeToStartGame() {
        final Disposable disposable = MessageSubject.startGameResponseSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<StartGameResponse>() {
                            @Override
                            public void accept(final StartGameResponse startGameResponse) throws Exception {
                                // ToDo
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(final Throwable throwable) throws Exception {
                                Log.d(TAG, throwable.getMessage());
                            }
                        }
                );

        addDisposable(disposable);
    }

    private void subscribeToFinishedGame() {
        final Disposable disposable = MessageSubject.finishedGameResponseSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<FinishedGameResponse>() {
                            @Override
                            public void accept(final FinishedGameResponse finishedGameResponse) throws Exception {
                                landingView.showStartGameFragment();

                                //ToDo: Delete sessionkey
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(final Throwable throwable) throws Exception {
                                Log.d(TAG, throwable.getMessage());
                            }
                        }
                );

        addDisposable(disposable);
    }

    private void subscribeToLoadSessionKey() {
        final Disposable disposable = sessionKeyRepository.getSessionKey()
                .subscribeOn(Schedulers.io())
                .defaultIfEmpty(new SessionKey())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<SessionKey>() {
                            @Override
                            public void accept(final SessionKey sessionKey) throws Exception {
                                if(sessionKey.getSessionKey() != null) {
                                    final RestartGameRequest restartGameRequest = new RestartGameRequest();
                                    restartGameRequest.sessionKey = sessionKey.getSessionKey();

                                    myApp.createConnection(restartGameRequest);
                                } else {
                                    landingView.showStartGameFragment();
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(final Throwable throwable) throws Exception {
                                Log.d(TAG, throwable.getMessage());
                            }
                        }
                );

        addDisposable(disposable);
    }
}
