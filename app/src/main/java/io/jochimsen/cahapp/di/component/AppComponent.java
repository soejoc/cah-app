package io.jochimsen.cahapp.di.component;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.di.module.ActivityBuilder;
import io.jochimsen.cahapp.di.module.AppModule;
import io.jochimsen.cahapp.di.module.RoomModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,
        RoomModule.class
})
public interface AppComponent extends AndroidInjector<MyApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(final MyApp application);
        AppComponent build();
    }

    @Override
    void inject(final MyApp instance);
}
