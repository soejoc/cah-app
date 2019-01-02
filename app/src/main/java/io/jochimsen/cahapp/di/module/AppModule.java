package io.jochimsen.cahapp.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;
import io.jochimsen.cahapp.MyApp;

@Module
public abstract class AppModule {

    @Binds
    abstract Context provideContext(final MyApp application);

    @Binds
    abstract Application provideApplication(final MyApp application);
}