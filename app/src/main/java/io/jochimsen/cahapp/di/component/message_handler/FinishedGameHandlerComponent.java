package io.jochimsen.cahapp.di.component.message_handler;

import dagger.BindsInstance;
import dagger.Subcomponent;
import io.jochimsen.cahapp.di.scope.MessageHandlerScope;
import io.jochimsen.cahapp.message_handler.FinishedGameHandler;
import io.jochimsen.cahapp.message_handler.StartGameHandler;
import io.jochimsen.cahframework.handler.message.MessageHandlerBase;
import io.jochimsen.cahframework.protocol.object.message.response.FinishedGameResponse;
import io.jochimsen.cahframework.protocol.object.message.response.StartGameResponse;

@MessageHandlerScope
@Subcomponent
public interface FinishedGameHandlerComponent extends MessageHandlerBaseComponent<FinishedGameHandler> {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        Builder protocolMessage(final FinishedGameResponse protocolMessage);
        FinishedGameHandlerComponent build();
    }
}
