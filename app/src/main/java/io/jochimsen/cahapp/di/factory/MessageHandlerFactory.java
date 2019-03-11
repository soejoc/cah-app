package io.jochimsen.cahapp.di.factory;

import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import io.jochimsen.cahapp.di.scope.AppScope;
import io.jochimsen.cahframework.handler.message.MessageHandler;
import lombok.AllArgsConstructor;

@AppScope
@AllArgsConstructor(onConstructor = @__({@Inject}))
public final class MessageHandlerFactory {
    private final Map<Class<? extends MessageHandler>, Provider<MessageHandler>> creators;

    @SuppressWarnings("unchecked")
    @NonNull
    public <T extends MessageHandler> T create(@NonNull final Class<T> clazz) {
        final Provider<? extends MessageHandler> creator = creators.get(clazz);

        if (creator == null) {
            throw new IllegalArgumentException("unknown message handler " + clazz);
        }

        return (T)creator.get();
    }
}
