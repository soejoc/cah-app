package io.jochimsen.cahapp.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.jochimsen.cahapp.di.scope.GameActivityScope;
import io.jochimsen.cahapp.ui.game.GameActivity;

@Module
public abstract class SessionActivityModule {

    @GameActivityScope
    @ContributesAndroidInjector(modules = GameActivityModule.class)
    abstract GameActivity bindGameActivity();
}
