package io.jochimsen.cahapp.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.jochimsen.cahapp.di.scope.MessageHandlerKey;
import io.jochimsen.cahapp.message_handler.FinishedGameHandler;
import io.jochimsen.cahapp.message_handler.StartGameHandler;
import io.jochimsen.cahframework.handler.message.MessageHandler;

@Module
public abstract class MessageHandlerModule {

    @Binds
    @IntoMap
    @MessageHandlerKey(StartGameHandler.class)
    abstract MessageHandler bindStartGameHandler(final StartGameHandler startGameHandler);

    @Binds
    @IntoMap
    @MessageHandlerKey(FinishedGameHandler.class)
    abstract MessageHandler bindFinishedGameHandler(final FinishedGameHandler finishedGameHandler);
}
