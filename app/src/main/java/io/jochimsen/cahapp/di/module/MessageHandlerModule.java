package io.jochimsen.cahapp.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.jochimsen.cahapp.di.scope.MessageHandlerKey;
import io.jochimsen.cahapp.message_handler.ClientMessageHandler;
import io.jochimsen.cahapp.message_handler.FinishedGameHandler;
import io.jochimsen.cahapp.message_handler.StartGameHandler;

@Module
public abstract class MessageHandlerModule {

    @Binds
    @IntoMap
    @MessageHandlerKey(StartGameHandler.class)
    abstract ClientMessageHandler bindStartGameHandler(final StartGameHandler startGameHandler);

    @Binds
    @IntoMap
    @MessageHandlerKey(FinishedGameHandler.class)
    abstract ClientMessageHandler bindFinishedGameHandler(final FinishedGameHandler finishedGameHandler);
}
