package io.jochimsen.cahapp.di.module;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.jochimsen.cahapp.di.scope.ActivityScope;
import io.jochimsen.cahapp.di.scope.FragmentScope;
import io.jochimsen.cahapp.ui.landing.StartGameFragment;
import io.jochimsen.cahapp.ui.WaitFragment;
import io.jochimsen.cahapp.ui.landing.WaitForCardSynchronizationFragment;

@Module
public abstract class LandingActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract StartGameFragment bindStartGameFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract WaitForCardSynchronizationFragment bindWaitForCardSynchronizationFragment();

    ////////////////// Provides

    @ActivityScope
    @Provides
    public static StartGameFragment provideStartGameFragment() {
        return new StartGameFragment();
    }

    @ActivityScope
    @Provides
    public static WaitFragment provideWaitFragment() {
        return new WaitFragment();
    }

    @ActivityScope
    @Provides
    public static WaitForCardSynchronizationFragment provideWaitForCardSynchronizationFragment() {
        return new WaitForCardSynchronizationFragment();
    }
}