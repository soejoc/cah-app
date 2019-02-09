package io.jochimsen.cahapp.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.di.scope.StartGameFragmentScope;
import io.jochimsen.cahapp.repository.BlackCardRepository;
import io.jochimsen.cahapp.repository.SessionKeyRepository;
import io.jochimsen.cahapp.repository.WhiteCardRepository;
import io.jochimsen.cahapp.ui.landing.LandingActivity;
import io.jochimsen.cahapp.ui.landing.LandingPresenter;
import io.jochimsen.cahapp.ui.landing.LandingPresenterImpl;
import io.jochimsen.cahapp.ui.landing.LandingView;
import io.jochimsen.cahapp.ui.landing.StartGameFragment;

@Module
public abstract class LandingActivityModule {

    @Binds
    abstract LandingView bindLandingView(final LandingActivity landingActivity);

    @Binds
    abstract LandingPresenter bindLandingPresenter(final LandingPresenterImpl landingPresenter);

    @StartGameFragmentScope
    @ContributesAndroidInjector(modules = StartGameFragmentModule.class)
    abstract StartGameFragment bindStartGameFragment();
}