package io.jochimsen.cahapp.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.jochimsen.cahapp.di.scope.LandingActivityScope;
import io.jochimsen.cahapp.di.scope.StartGameFragmentScope;
import io.jochimsen.cahapp.ui.landing.LandingActivity;
import io.jochimsen.cahapp.ui.landing.LandingPresenter;
import io.jochimsen.cahapp.ui.landing.LandingPresenterImpl;
import io.jochimsen.cahapp.ui.landing.LandingView;
import io.jochimsen.cahapp.ui.landing.StartGameFragment;
import io.jochimsen.cahapp.ui.landing.WaitFragment;

@Module
public abstract class LandingActivityModule {

    @LandingActivityScope
    @Binds
    abstract LandingView bindLandingView(final LandingActivity landingActivity);

    @LandingActivityScope
    @Binds
    abstract LandingPresenter bindLandingPresenter(final LandingPresenterImpl landingPresenter);

    @StartGameFragmentScope
    @ContributesAndroidInjector(modules = StartGameFragmentModule.class)
    abstract StartGameFragment bindStartGameFragment();

    @LandingActivityScope
    @Provides
    public static StartGameFragment provideStartGameFragment() {
        return new StartGameFragment();
    }

    @LandingActivityScope
    @Provides
    public static WaitFragment provideWaitFragment() {
        return new WaitFragment();
    }
}