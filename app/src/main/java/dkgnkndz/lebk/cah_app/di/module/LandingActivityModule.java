package dkgnkndz.lebk.cah_app.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dkgnkndz.lebk.cah_app.MyApp;
import dkgnkndz.lebk.cah_app.repository.SessionKeyRepository;
import dkgnkndz.lebk.cah_app.ui.landing.LandingActivity;
import dkgnkndz.lebk.cah_app.ui.landing.LandingPresenter;
import dkgnkndz.lebk.cah_app.ui.landing.LandingPresenterImpl;
import dkgnkndz.lebk.cah_app.ui.landing.LandingView;

@Module
public abstract class LandingActivityModule {

    @Binds
    abstract LandingView bindLandingView(final LandingActivity landingActivity);

    @Provides
    static LandingPresenter provideLandingPresenter(final LandingView landingView, final SessionKeyRepository sessionKeyRepository, final MyApp myApp) {
        return new LandingPresenterImpl(landingView, sessionKeyRepository, myApp);
    }
}