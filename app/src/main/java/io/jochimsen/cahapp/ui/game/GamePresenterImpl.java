package io.jochimsen.cahapp.ui.game;

import javax.inject.Inject;

public class GamePresenterImpl implements GamePresenter {

    private final GameView gameView;

    @Inject
    public GamePresenterImpl(final GameView gameView) {
        this.gameView = gameView;
    }
}
