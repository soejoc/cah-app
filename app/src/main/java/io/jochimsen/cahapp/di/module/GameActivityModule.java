package io.jochimsen.cahapp.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.repository.BlackCardRepository;
import io.jochimsen.cahapp.repository.SessionKeyRepository;
import io.jochimsen.cahapp.repository.WhiteCardRepository;
import io.jochimsen.cahapp.ui.game.GameActivity;
import io.jochimsen.cahapp.ui.game.GamePresenter;
import io.jochimsen.cahapp.ui.game.GamePresenterImpl;
import io.jochimsen.cahapp.ui.game.GameView;
import io.jochimsen.cahapp.ui.landing.LandingActivity;
import io.jochimsen.cahapp.ui.landing.LandingPresenter;
import io.jochimsen.cahapp.ui.landing.LandingPresenterImpl;
import io.jochimsen.cahapp.ui.landing.LandingView;

@Module
public abstract class GameActivityModule {

    @Binds
    abstract GameView bindGameView(final GameActivity gameActivity);

    @Provides
    static GamePresenter provideGamePresenter(final GameView gameView) {
        return new GamePresenterImpl(gameView);
    }
}