package io.jochimsen.cahapp.di.component;

import dagger.BindsInstance;
import dagger.Subcomponent;
import io.jochimsen.cahapp.di.component.message_handler.FinishedGameHandlerComponent;
import io.jochimsen.cahapp.di.component.message_handler.StartGameHandlerComponent;
import io.jochimsen.cahapp.di.module.SessionModule;
import io.jochimsen.cahapp.di.scope.SessionScope;
import io.jochimsen.cahapp.network.session.ServerSession;

@SessionScope
@Subcomponent(modules = {
        SessionModule.class
})
public interface SessionComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        Builder serverSession(final ServerSession serverSession);
        SessionComponent build();
    }

    GameActivityComponent.Builder gameActivityComponentBuilder();

    StartGameHandlerComponent.Builder startGameHandlerBuilder();
    FinishedGameHandlerComponent.Builder finishedGameHandlerBuilder();
}
