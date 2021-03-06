package io.jochimsen.cahapp.di.component;

import dagger.BindsInstance;
import dagger.Subcomponent;
import io.jochimsen.cahapp.di.scope.SessionScope;
import io.jochimsen.cahapp.network.session.ServerSession;

@SessionScope
@Subcomponent
public interface SessionComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        Builder serverSession(final ServerSession serverSession);
        SessionComponent build();
    }

    GameActivityComponent.Builder gameActivityComponentBuilder();
}
