package io.jochimsen.cahapp.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.network.handler.MessageSubject;
import io.jochimsen.cahapp.repository.BlackCardRepository;
import io.jochimsen.cahapp.repository.PlayerRepository;
import io.jochimsen.cahapp.repository.SessionKeyRepository;
import io.jochimsen.cahapp.repository.WhiteCardRepository;
import io.jochimsen.cahapp.ui.landing.LandingActivity;
import io.jochimsen.cahapp.ui.landing.LandingPresenter;
import io.jochimsen.cahapp.ui.landing.LandingPresenterImpl;
import io.jochimsen.cahapp.ui.landing.LandingView;

@Module
public abstract class LandingActivityModule {

    @Binds
    abstract LandingView bindLandingView(final LandingActivity landingActivity);

    @Provides
    static LandingPresenter provideLandingPresenter(final LandingView landingView, final SessionKeyRepository sessionKeyRepository, final WhiteCardRepository whiteCardRepository,
                                                    final BlackCardRepository blackCardRepository, final PlayerRepository playerRepository, final MyApp myApp) {
        return new LandingPresenterImpl(landingView, sessionKeyRepository, whiteCardRepository, blackCardRepository, playerRepository, myApp);
    }
}