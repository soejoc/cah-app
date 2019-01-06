package io.jochimsen.cahapp.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.jochimsen.cahapp.ui.game.GameActivity;
import io.jochimsen.cahapp.ui.landing.LandingActivity;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = LandingActivityModule.class)
    abstract LandingActivity bindLandingActivity();

    @ContributesAndroidInjector(modules = GameActivityModule.class)
    abstract GameActivity bindGameActivity();
}
