package io.jochimsen.cahapp.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.jochimsen.cahapp.di.scope.ActivityScope;
import io.jochimsen.cahapp.ui.landing.LandingActivity;

@Module
public abstract class AppActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = LandingActivityModule.class)
    abstract LandingActivity bindLandingActivity();
}
