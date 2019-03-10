package io.jochimsen.cahapp.di.module;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.jochimsen.cahapp.di.scope.ActivityScope;
import io.jochimsen.cahapp.di.scope.FragmentScope;
import io.jochimsen.cahapp.ui.WaitFragment;
import io.jochimsen.cahapp.ui.landing.StartGameFragment;
import io.jochimsen.cahapp.ui.landing.WaitForCardSynchronizationFragment;

@Module
abstract class LandingActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract StartGameFragment bindStartGameFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract WaitForCardSynchronizationFragment bindWaitForCardSynchronizationFragment();

    ////////////////// Provides

    @ActivityScope
    @Provides
    static StartGameFragment provideStartGameFragment() {
        return new StartGameFragment();
    }

    @ActivityScope
    @Provides
    static WaitFragment provideWaitFragment() {
        return new WaitFragment();
    }

    @ActivityScope
    @Provides
    static WaitForCardSynchronizationFragment provideWaitForCardSynchronizationFragment() {
        return new WaitForCardSynchronizationFragment();
    }
}