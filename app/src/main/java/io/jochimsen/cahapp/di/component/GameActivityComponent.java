package io.jochimsen.cahapp.di.component;

import dagger.BindsInstance;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import io.jochimsen.cahapp.di.module.GameActivityModule;
import io.jochimsen.cahapp.ui.game.GameActivity;

@Subcomponent(modules = {
        GameActivityModule.class
})
public interface GameActivityComponent extends AndroidInjector<GameActivity> {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        Builder gameActivity(final GameActivity gameActivity);
        GameActivityComponent build();
    }

    @Override
    void inject(final GameActivity instance);
}
