package io.jochimsen.cahapp.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.jochimsen.cahapp.di.scope.LandingActivityScope;
import io.jochimsen.cahapp.ui.game.GameActivity;
import io.jochimsen.cahapp.ui.landing.LandingActivity;

@Module
public abstract class AppActivityModule {

    @LandingActivityScope
    @ContributesAndroidInjector(modules = LandingActivityModule.class)
    abstract LandingActivity bindLandingActivity();
}
