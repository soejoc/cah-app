package dkgnkndz.lebk.cah_app.di.component;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dkgnkndz.lebk.cah_app.MyApp;
import dkgnkndz.lebk.cah_app.di.module.ActivityBuilder;
import dkgnkndz.lebk.cah_app.di.module.AppModule;
import dkgnkndz.lebk.cah_app.di.module.RoomModule;

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
