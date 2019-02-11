package io.jochimsen.cahapp.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.jochimsen.cahapp.ui.game.GameActivity;
import io.jochimsen.cahapp.ui.game.GamePresenter;
import io.jochimsen.cahapp.ui.game.GamePresenterImpl;
import io.jochimsen.cahapp.ui.game.GameView;

@Module
public abstract class GameActivityModule {

    @Binds
    abstract GameView bindGameView(final GameActivity gameActivity);

    @Provides
    static GamePresenter provideGamePresenter(final GameView gameView) {
        return new GamePresenterImpl(gameView);
    }
}