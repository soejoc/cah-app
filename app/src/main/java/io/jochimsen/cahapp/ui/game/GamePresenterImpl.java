package io.jochimsen.cahapp.ui.game;

import javax.inject.Inject;

import io.jochimsen.cahapp.network.handler.MessageSubject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class GamePresenterImpl implements GamePresenter {

    private final GameView gameView;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public GamePresenterImpl(final GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
    }
}
