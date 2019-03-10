package io.jochimsen.cahapp.di.component.message_handler;

import dagger.BindsInstance;
import dagger.Subcomponent;
import io.jochimsen.cahapp.di.scope.MessageHandlerScope;
import io.jochimsen.cahapp.message_handler.StartGameHandler;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;

@MessageHandlerScope
@Subcomponent
public interface StartGameHandlerComponent extends MessageHandlerBaseComponent<StartGameHandler> {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        Builder protocolMessage(final StartGameResponse protocolMessage);
        StartGameHandlerComponent build();
    }
}
