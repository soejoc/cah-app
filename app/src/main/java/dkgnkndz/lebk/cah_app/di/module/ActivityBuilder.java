package dkgnkndz.lebk.cah_app.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dkgnkndz.lebk.cah_app.ui.landing.LandingActivity;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = LandingActivityModule.class)
    abstract LandingActivity bindLandingActivity();
}
