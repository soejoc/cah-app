package io.jochimsen.cahapp.di.factory;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import io.jochimsen.cahapp.di.scope.AppScope;
import io.jochimsen.cahapp.message_handler.ClientMessageHandler;
import io.jochimsen.cahapp.network.session.ServerSession;
import io.jochimsen.cahframework.handler.message.MessageHandler;
import io.jochimsen.cahframework.protocol.object.message.ResponseMessage;
import io.jochimsen.cahframework.util.MessageHandlerFactory;
import lombok.AllArgsConstructor;

@AppScope
@AllArgsConstructor(onConstructor = @__({@Inject}))
public final class ClientMessageHandlerFactory implements MessageHandlerFactory<ResponseMessage, ServerSession> {
    private final Map<Class<? extends ClientMessageHandler>, Provider<ClientMessageHandler>> creators;

    @SuppressWarnings({"SuspiciousMethodCalls", "unchecked"})
    @Override
    public MessageHandler<? extends ResponseMessage, ServerSession> create(final Class<? extends MessageHandler<? extends ResponseMessage, ServerSession>> clazz) {
        final Provider<? extends ClientMessageHandler> creator = creators.get(clazz);

        if (creator == null) {
            throw new IllegalArgumentException("unknown message handler " + clazz);
        }

        return creator.get();
    }
}
