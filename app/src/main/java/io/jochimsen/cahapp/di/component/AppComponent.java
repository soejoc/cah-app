package io.jochimsen.cahapp.di.component;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import io.jochimsen.cahapp.MyApp;
import io.jochimsen.cahapp.di.module.AppActivityModule;
import io.jochimsen.cahapp.di.module.AppModule;
import io.jochimsen.cahapp.di.module.ConnectionModule;
import io.jochimsen.cahapp.di.module.MessageSubjectModule;
import io.jochimsen.cahapp.di.module.RoomModule;
import io.jochimsen.cahapp.di.scope.AppScope;

@AppScope
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        AppActivityModule.class,
        RoomModule.class,
        MessageSubjectModule.class
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

    NetworkComponent networkComponent(final ConnectionModule connectionModule);
}
