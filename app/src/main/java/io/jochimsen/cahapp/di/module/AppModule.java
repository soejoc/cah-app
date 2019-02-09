package io.jochimsen.cahapp.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;
import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.di.qualifier.ApplicationContext;

@Module
public abstract class AppModule {

    @ApplicationContext
    @Binds
    abstract Context provideContext(final MyApp application);

    @Binds
    abstract Application provideApplication(final MyApp application);
}